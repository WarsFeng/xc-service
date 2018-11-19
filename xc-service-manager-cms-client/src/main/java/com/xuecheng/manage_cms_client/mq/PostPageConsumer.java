package com.xuecheng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.manage_cms_client.service.CmsPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: wars
 * \* Date: 18-11-18
 * \* Time: 下午2:31
 * \* Description:
 * \
 */
@Slf4j
@Component
public class PostPageConsumer {

    private CmsPageService cmsPageService;

    @Autowired
    public PostPageConsumer(CmsPageService cmsPageService) {
        this.cmsPageService = cmsPageService;
    }

    @RabbitListener(queues = {"${xuecheng.mq.queue}"})
    public void postPage(String msg) {
        log.info("Receive cms post page: {}", msg);
        Map map = JSON.parseObject(msg, Map.class);
        String pageId = (String) map.get("pageId");
        cmsPageService.savePageToServerPath(pageId);
    }
}
