package com.xuecheng.manage_cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.config.RabbitConfig;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import com.xuecheng.manage_cms.service.CmsPageService;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-9-18
 * \* Time: 下午12:55
 * \* Description:
 * \
 */
@Service
public class CmsPageServiceImpl implements CmsPageService {

    private final CmsPageRepository repository;
    private final CmsTemplateRepository cmsTemplateRepository;
    private final RestTemplate restTemplate;
    private final GridFsTemplate gridFsTemplate;
    private final GridFSBucket gridFSBucket;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public CmsPageServiceImpl(CmsPageRepository repository, CmsTemplateRepository cmsTemplateRepository, RestTemplate restTemplate, GridFsTemplate gridFsTemplate, GridFSBucket gridFSBucket, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.cmsTemplateRepository = cmsTemplateRepository;
        this.restTemplate = restTemplate;
        this.gridFsTemplate = gridFsTemplate;
        this.gridFSBucket = gridFSBucket;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        // Page and size process
        if (0 >= page)
            page = 1;
        // Abstract page to real page
        --page;
        if (0 >= size)
            size = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "pageCreateTime"));

        //  Custom conditional find
        Page<CmsPage> pages;
        if (queryPageRequest.isNotEmpty()) {
            Example<CmsPage> example;
            CmsPage probe = new CmsPage();
            if (isNotEmpty(queryPageRequest.getSiteId()))
                probe.setSiteId(queryPageRequest.getSiteId());
            if (isNotEmpty(queryPageRequest.getTemplateId()))
                probe.setTemplateId(queryPageRequest.getTemplateId());

            // Fuzzy find(contains)
            if (isNotEmpty(queryPageRequest.getPageAlias())) {
                ExampleMatcher matching = ExampleMatcher.matching();
                probe.setPageAlias(queryPageRequest.getPageAlias());
                matching = matching.withMatcher("pageAlias", ExampleMatcher.GenericPropertyMatchers.contains());

                example = Example.of(probe, matching);
            } else
                example = Example.of(probe);
            // Execute query
            pages = repository.findAll(example, pageable);
        } else
            pages = repository.findAll(pageable);

        // Result
        QueryResult<CmsPage> queryResult = new QueryResult<>();
        queryResult.setList(pages.getContent());
        queryResult.setTotal(pages.getTotalElements());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }


    @Override
    public CmsPageResult add(CmsPage cmsPage) {
        // Check if it already exists
        CmsPage index = repository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        // Exist
        if (null != index)
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);

        // Insert and return
        cmsPage.setPageId(null);    //  SpringDataJpa auto generate
        return new CmsPageResult(CommonCode.SUCCESS, repository.insert(cmsPage));
    }

    @Override
    public CmsPage findById(String id) {
        if (isEmpty(id))
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOT_EXISTS);
        Optional<CmsPage> cmsPage = repository.findById(id);
        return cmsPage.orElse(null);
    }

    @Override
    public CmsPageResult edit(String id, CmsPage cmsPage) {
        CmsPage page = this.findById(id);

        // 更新站点名称
        page.setPageName(cmsPage.getPageName());
        // 更新站点Id
        page.setSiteId(cmsPage.getSiteId());
        // 更新模板Id
        page.setTemplateId(cmsPage.getTemplateId());
        // 更新Web访问路径
        page.setPageWebPath(cmsPage.getPageWebPath());
        // 更新实际文件路径
        page.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
        // 更新数据Url
        page.setDataUrl(cmsPage.getDataUrl());
        // 更新页面类型
        page.setPageType(cmsPage.getPageType());
        // 更新页面别名
        page.setPageAlias(cmsPage.getPageAlias());
        // 更新页面创建时间
        page.setPageCreateTime(cmsPage.getPageCreateTime());

        return new CmsPageResult(CommonCode.SUCCESS, repository.save(page));
    }

    @Override
    public String getPageHtml(String id) {
        // Get model
        Map model = getModelByPageId(id);
        if (null == model)
            ExceptionCast.cast(CmsCode.CMS_GENERATE_HTML_DATA_ISNULL);

        // Get template
        String templateContent = getTemplateByPageId(id);
        if (isEmpty(templateContent))
            ExceptionCast.cast(CmsCode.CMS_GENERATE_HTML_TEMPLATE_ISNULL);

        // Generate html
        String htmlContent = generateHtml(templateContent, model);
        if (isEmpty(htmlContent))
            ExceptionCast.cast(CmsCode.CMS_GENERATE_HTML_HTML_ISNULL);
        return htmlContent;
    }

    private String generateHtml(String templateContent, Map model) {
        // Init configuration
        Configuration configuration = new Configuration(Configuration.getVersion());
        // Template loader
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        templateLoader.putTemplate("template", templateContent);
        // Generate template class
        configuration.setTemplateLoader(templateLoader);
        try {
            Template template = configuration.getTemplate("template");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map getModelByPageId(String id) {
        // Get data url
        CmsPage page = this.findById(id);
        if (null == page)
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOT_EXISTS);
        String dataUrl = page.getDataUrl();
        if (isEmpty(dataUrl))
            ExceptionCast.cast(CmsCode.CMS_GENERATE_HTML_DATA_URL_ISNULL);
        // Get model
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        return forEntity.getBody();
    }

    private String getTemplateByPageId(String id) {
        // Get template file id
        CmsPage page = findById(id);
        if (null == page)
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOT_EXISTS);
        String templateId = page.getTemplateId();
        if (isEmpty(templateId))
            ExceptionCast.cast(CmsCode.CMS_GENERATE_HTML_TEMPLATE_ISNULL);
        Optional<CmsTemplate> template = cmsTemplateRepository.findById(templateId);
        if (!template.isPresent())
            ExceptionCast.cast(CmsCode.CMS_GENERATE_HTML_TEMPLATE_ISNULL);
        String templateFileId = template.get().getTemplateFileId();

        // Get template data
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
        if (null == gridFSFile)
            ExceptionCast.cast(CmsCode.CMS_GENERATE_HTML_TEMPLATE_FILE_NOT_EXISTS);
        // Open download stream
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        try {
            // Stream to text
            return IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseResult delete(String id) {
        CmsPage cmsPage = findById(id);
        if (null == cmsPage)
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOT_EXISTS);

        repository.deleteById(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    public ResponseResult postPage(String id) {
        // Get page string
        String pageHtml = getPageHtml(id);
        if (isEmpty(pageHtml))
            ExceptionCast.cast(CmsCode.CMS_GENERATE_HTML_HTML_ISNULL);

        // Save static file
        CmsPage page = saveHtml(id, pageHtml);
        // Send message
        sendPostPage(id);

        return new ResponseResult(CommonCode.SUCCESS);
    }

    private CmsPage saveHtml(String id, String pageHtml) {
        Optional<CmsPage> cmsPage = repository.findById(id);
        if (!cmsPage.isPresent())
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOT_EXISTS);
        CmsPage page = cmsPage.get();

        // Delete the html if it exist
        String htmlFileId = page.getHtmlFileId();
        if (isNotEmpty(htmlFileId))
            gridFsTemplate.delete(Query.query(Criteria.where("_id").is(htmlFileId)));

        // Convert to inputStream
        InputStream inputStream = IOUtils.toInputStream(pageHtml, Charsets.UTF_8);
        // Save to mongodb
        ObjectId objectId = gridFsTemplate.store(inputStream, page.getPageName());

        // Update page of the database
        page.setHtmlFileId(objectId.toHexString());
        return repository.save(page);
    }

    /**
     * Send post page message to rabbitmq
     * Message: { 'pageId' : ''}
     *
     * @param id Page id
     */
    private void sendPostPage(String id) {
        // Message
        Map<String, String> message = new HashMap<>(1);
        message.put("pageId", id);

        Optional<CmsPage> cmsPage = repository.findById(id);
        if (!cmsPage.isPresent())
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOT_EXISTS);
        // Send
        rabbitTemplate.convertAndSend(
                RabbitConfig.EX_ROUTING_CMS_POSTPAGE,
                cmsPage.get().getSiteId(),
                JSON.toJSONString(message));
    }
}
