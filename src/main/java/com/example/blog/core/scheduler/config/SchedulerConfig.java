package com.example.blog.core.scheduler.config;

import com.example.blog.core.client.RsaService;
import com.example.blog.core.scheduler.RsaScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Profile("!local")
public class SchedulerConfig {
    @Bean
    public RsaScheduler rsaScheduler(RsaService rsaService) {
        return new RsaScheduler(rsaService);
    }
}
