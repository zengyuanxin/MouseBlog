package com.mouse.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 星星
 * @create 2023-02-15 15:56
 */
@Component
public class TestJob {
    @Scheduled(cron = "0/5 * * * * ?")
    public void testJob(){
        //要执行的代码
//        System.out.println("定时任务执行了");
    }
}
