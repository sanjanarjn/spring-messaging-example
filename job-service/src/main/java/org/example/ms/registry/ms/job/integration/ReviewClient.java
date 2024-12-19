package org.example.ms.registry.ms.job.integration;

import org.example.ms.registry.ms.job.external.Company;
import org.example.ms.registry.ms.job.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "review-service")
public interface ReviewClient {

    @GetMapping("/review")
    List<Review> getReviews(@RequestParam long companyId);
}
