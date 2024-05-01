package com.parking.smart.sp_parking_api;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class SpParkingApiApplication implements WebMvcConfigurer {

    private final Environment env;

    public static void main(String[] args) {
        SpringApplication.run(SpParkingApiApplication.class, args);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해
                .allowedOrigins("*") // 모든 도메인을 허용
                .allowedHeaders("*") // 모든 헤더를 허용
                .allowedMethods("GET", "POST", "DELETE", "PUT");
    }

    @PostConstruct
    private void initialize() {
        Locale.setDefault(Locale.KOREA); // 시스템 로케일을 한국으로 설정
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul")); // 시스템 시간을 한국 시간으로 설정
        System.setProperty("aws.ec2.metadata.disabled", "true"); // AWS SDK가 EC2 인스턴스 메타데이터를 조회하지 않도록 설정
    }

    private void monitoring() {
        try(ScheduledExecutorService thread = new ScheduledThreadPoolExecutor(1)) {
            thread.scheduleWithFixedDelay(() -> {
                System.gc();
                Runtime runtime = Runtime.getRuntime();
                log.info("Max Memory   ::: {} MB ", runtime.maxMemory() / 1024 * 1024);
                log.info("Using Memory ::: {} MB", (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024));
            }, 1, 1, TimeUnit.MINUTES);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void applicationReadyEvent() {
        log.info("Application Name ::: {}", env.getProperty("spring.application.name"));
        log.info("Locale           ::: {}", Locale.getDefault());
        log.info("Thread Limit     ::: {}", env.getProperty("server.tomcat.threads.max"));
        log.info("Connection Limit ::: {}", env.getProperty("server.tomcat.max-connections"));
        log.info("Hikari Pool Size ::: {}", env.getProperty("spring.datasource.hikari.maximum-pool-size"));
        monitoring();
    }

}
