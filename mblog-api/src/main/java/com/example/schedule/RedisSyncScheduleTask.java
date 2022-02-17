package com.example.schedule;

import com.example.config.RedisKeyConfig;
import com.example.entity.Blog;
import com.example.service.BlogService;
import com.example.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * @author giaming
 * @date 2022/2/12 - 21:56
 * @detail
 */
@Component
@EnableScheduling
@EnableAsync
public class RedisSyncScheduleTask {

    @Autowired
    private RedisService redisService;
    @Autowired
    private BlogService blogService;

    Logger logger = LoggerFactory.getLogger(RedisSyncScheduleTask.class);


    /**
     * 从redis同步博客文章浏览量到数据库
     * */
    @Async
    @Scheduled(fixedDelay = 24*60*60*1000)  // 间隔24小时
    public void syncBlogViewsToDatabase() {
       logger.info("执行定时任务");
       String redisKey = RedisKeyConfig.BLOG_VIEWS_MAP;
        Map blogViewMap = redisService.getMapByHash(redisKey);
        Set<Integer> keys = blogViewMap.keySet();
        for (Integer key : keys) {
            Integer views = (Integer) blogViewMap.get(key);
            Blog blog = blogService.getById(key);
            blog.setViews(blog.getViews()+views);
            blogService.saveOrUpdate(blog);
        }
        deleteAllCache();
        logger.info("完成任务");
    }

    /**
     * 清除所有缓存
     */
    private void deleteAllCache() {
        redisService.deleteAllKeys();
    }

}
