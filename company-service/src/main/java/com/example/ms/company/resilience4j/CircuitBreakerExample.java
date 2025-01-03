package com.example.ms.company.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

import java.time.Duration;
import java.util.function.Supplier;

public class CircuitBreakerExample {
    public static void main(String[] args) {
        // Configure the CircuitBreaker with custom properties
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .slowCallRateThreshold(40)
                .slidingWindowSize(100)
                .waitDurationInOpenState(Duration.ofSeconds(10))
                .build();

        // Create a CircuitBreaker registry with the custom config
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
        CircuitBreaker circuitBreaker = registry.circuitBreaker("myCircuitBreaker", config);

        // Create a decorated Supplier that uses the circuit breaker
        Supplier<String> decoratedSupplier = CircuitBreaker.decorateSupplier(circuitBreaker, CircuitBreakerExample::fetchData);

        // Simulate calling the decorated Supplier
        for (int i = 0; i < 10; i++) {
            try {
                String result = decoratedSupplier.get();
                System.out.println("Call successful: " + result);
            } catch (Exception e) {
                System.out.println("CircuitBreaker opened, fallback executed: " + e.getMessage());
            }
        }
    }

    // Simulated method that may throw an exception (representing a failing service)
    public static String fetchData() {
        if (Math.random() > 0.5) {
            return "Data fetched successfully";
        } else {
            throw new RuntimeException("Service is down!");
        }
    }
}