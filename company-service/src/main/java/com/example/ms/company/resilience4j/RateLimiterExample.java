package com.example.ms.company.resilience4j;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import java.time.Duration;

public class RateLimiterExample {

    public static void main(String[] args) {

        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(5)
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .timeoutDuration(Duration.ofMillis(500)).build();
        RateLimiter rateLimiter = RateLimiter.of("myRateLimiter", config);

        Runnable decoratedRunnable = RateLimiter.decorateRunnable(rateLimiter, RateLimiterExample::processRequest);

        for (int i = 0; i < 10; i++) {
            try {
                decoratedRunnable.run();  // Executes the task with rate limiting
            } catch (Exception e) {
                System.out.println("Rate limit exceeded: " + e.getMessage());
            }
        }
    }
    public static void processRequest() {
        System.out.println("Processing request...");
    }
}