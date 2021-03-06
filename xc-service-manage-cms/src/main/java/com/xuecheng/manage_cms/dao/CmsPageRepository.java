package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CmsPageRepository extends MongoRepository<CmsPage, String> {

    /**
     * 根据页面名称查询页面
     * @param pageName 页面名称
     * @return CmsPage
     */
    CmsPage findByPageName(String pageName);

    /**
     * 根据页面名称和页面类型查询页面
     *
     * @param pageName 页面名称
     * @param pageType 页面类型
     * @return CmsPage
     */
    CmsPage findByPageNameAndPageType(String pageName, String pageType);

    /**
     * 根据站点id和页面类型查询记录数
     *
     * @param siteId   站点Id
     * @param pageType 页面类型
     */
    int countBySiteIdAndPageType(String siteId, String pageType);

    /**
     * 根据站点Id和页面类型分页查询
     *
     * @param siteId   站点Id
     * @param pageType 页面类型
     * @param pageable 分页条件
     */
    Page<CmsPage> findBySiteIdAndPageType(String siteId, String pageType, Pageable pageable);

    /**
     * 根据站点Id，页面名称，WebPath查询CmsPage
     *
     * @param pageName    页面名称
     * @param siteId      站点Id
     * @param pageWebPath 访问路径
     * @return 页面实体
     */
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName, String siteId, String pageWebPath);

}
