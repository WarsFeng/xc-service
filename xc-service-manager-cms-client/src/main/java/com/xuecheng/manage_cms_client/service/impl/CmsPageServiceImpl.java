package com.xuecheng.manage_cms_client.service.impl;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.dao.CmsSiteRepository;
import com.xuecheng.manage_cms_client.service.CmsPageService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: wars
 * \* Date: 18-11-17
 * \* Time: 下午2:52
 * \* Description:
 * \
 */
@Service
public class CmsPageServiceImpl implements CmsPageService {


    private CmsPageRepository repository;
    private CmsSiteRepository siteRepository;
    private GridFsTemplate gridFsTemplate;
    private GridFSBucket gridFSBucket;

    @Autowired
    public void getRepository(CmsPageRepository repository,
                              GridFSBucket gridFSBucket,
                              CmsSiteRepository siteRepository,
                              GridFsTemplate gridFsTemplate) {
        this.repository = repository;
        this.gridFSBucket = gridFSBucket;
        this.siteRepository = siteRepository;
        this.gridFsTemplate = gridFsTemplate;
    }

    @Override
    public void savePageToServerPath(String pageId) {
        // Get page html object id
        Optional<CmsPage> cmsPage = getPageByPageId(pageId);
        if (!cmsPage.isPresent())
            ExceptionCast.cast(CmsCode.CMS_PAGE_FILE_NOT_EXISTS);
        CmsPage page = cmsPage.get();

        // Get page save path(SitePath+PagePhysicalPath+PageName)
        Optional<CmsSite> cmsSite = getSiteBySiteId(page.getSiteId());
        if (!cmsSite.isPresent())
            ExceptionCast.cast(CmsCode.CMS_SITE_NOT_EXISTS);
        String pagePath = cmsSite.get().getSiteWebPath() + page.getPagePhysicalPath() + page.getPageName();

        // Download page html
        try (
                InputStream in = getFileById(page.getHtmlFileId());
                FileOutputStream out = new FileOutputStream(pagePath)
        ) {
            if (null == in)
                ExceptionCast.cast(CmsCode.CMS_GENERATE_HTML_HTML_ISNULL);
            // Save page to path
            IOUtils.copy(in, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Optional<CmsPage> getPageByPageId(String pageId) {
        return repository.findById(pageId);
    }

    private Optional<CmsSite> getSiteBySiteId(String siteId) {
        return siteRepository.findById(siteId);
    }

    private InputStream getFileById(String fileObjectId) {
        // Get gridFs File
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(fileObjectId)));
        // Get Stream
        try {
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
            return gridFsResource.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
