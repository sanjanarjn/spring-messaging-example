package com.example.ms.company.resilience4j;
import com.example.ms.company.CompanyNotFoundException;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

import java.time.Duration;

public class RetryExample {

    public static void main(String[] args) {
        // 2, 4, 6 seconds between first 3 attempts, thereafter 10 seconds
        IntervalFunction customIntervalFunction = (attempt) -> attempt < 3 ? Duration.ofSeconds(2 * attempt).toMillis() : Duration.ofSeconds(10).toMillis();

        // Create a Retry configuration with custom logic
        RetryConfig retryConfig = RetryConfig.custom().maxAttempts(5).intervalFunction(customIntervalFunction).build();
        Retry retry = Retry.of("customRetry", retryConfig);

        Runnable decoratedRunnable = Retry.decorateRunnable(retry, RetryExample::mockServiceCall);

        try {
            decoratedRunnable.run();
        } catch (Exception e) {
            System.out.println("All retry attempts failed: " + e.getMessage());
        }
    }

    // Mock service call to simulate failures and retries
    public static String mockServiceCall() throws CompanyNotFoundException {
        System.out.println("Attempting service call...");
        throw new RuntimeException("Simulated failure!"); // Simulates a failure triggering retries
    }
}