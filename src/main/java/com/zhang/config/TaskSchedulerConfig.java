package com.zhang.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by 直到世界尽头 on 2018/3/30.
 */
@Configuration
@ComponentScan("com.zhang.service.schedulServiceImpl")
@EnableScheduling
public class TaskSchedulerConfig {

}
