package com.miaoshaproject.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author wangshuo
 * @Date 2022/5/4, 18:37
 * 定时器服务
 */
@Slf4j
@Component
@Async
public class ScheduledService {

    //@Scheduled(cron = "0/5 * * * * *")
    public void scheduled(){
        //log.info("=====>>>>>使用cron  {}",System.currentTimeMillis());
    }

    //@Scheduled(fixedRate = 5000)
    public void scheduled1() {
        //log.info("=====>>>>>使用fixedRate{}", System.currentTimeMillis());
    }

    //@Scheduled(fixedDelay = 5000)
    public void scheduled2() {
        //log.info("=====>>>>>fixedDelay{}",System.currentTimeMillis());
    }

}
