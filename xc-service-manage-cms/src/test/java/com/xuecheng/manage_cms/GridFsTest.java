package com.xuecheng.manage_cms;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: wars
 * \* Date: 18-10-21
 * \* Time: 下午12:57
 * \* Description:
 * \
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GridFsTest {

    @Autowired
    GridFsTemplate template;
    @Autowired
    GridFSBucket bucket;

    private String id = "5bcc2e5cdb9715049e07974a";

    @Before
    public void testUpload() throws FileNotFoundException {
        FileInputStream input = new FileInputStream("/mnt/ntfs/Develop/Career/JavaEE/06_大型微服务项目/4/页面静态化/代码/index_banner.ftl");
        ObjectId objectId = template.store(input, "index_banner.ftl");
        Assert.assertNotNull(objectId);
        id = objectId.toString();
    }

    @Test
    public void testDownload() throws IOException {
        GridFSFile gridFSFile = template.findOne(Query.query(Criteria.where("_id").is(this.id)));
        Assert.assertNotNull(gridFSFile);
        GridFSDownloadStream gridFSDownloadStream = bucket.openDownloadStream(gridFSFile.getObjectId());
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        int length = IOUtils.copy(gridFsResource.getInputStream(), new FileOutputStream("/home/wars/" + gridFsResource.getFilename()));
        Assert.assertNotEquals(length, -1);
    }

    @After
    public void testDelete() {
        template.delete(Query.query(Criteria.where("_id").is(this.id)));
    }

}
