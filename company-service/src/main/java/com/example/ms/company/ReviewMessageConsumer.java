package com.example.ms.company;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ReviewMessageConsumer {

    private final CompanyService companyService;

    public  ReviewMessageConsumer(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RabbitListener(queues = "companyRating")
    public void consumeMessage(ReviewMessage reviewMessage) {
        companyService.updateReviewInCompany(reviewMessage);
    }
}
