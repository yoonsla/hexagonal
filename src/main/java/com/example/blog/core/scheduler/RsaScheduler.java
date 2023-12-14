package com.example.blog.core.scheduler;

import com.example.blog.core.client.RsaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
@Log4j2
public class RsaScheduler {

    private final RsaService rsaService;

    @Scheduled(cron = "0 0 0/1 * * *")
    public void setCacheServiceSchedule() {
        log.info("====================== RSA KEY RELOAD ======================");
        rsaService.refreshRsa();
        log.info("================== COMPLETE RSA KEY RELOAD =================");
    }
}
