package com.example.messaging.company;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ReviewMessageConsumer {

    private final CompanyService companyService;

    public  ReviewMessageConsumer(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RabbitListener(queues = "companyRating")
    public void consumeMessage(ReviewDto reviewDto) {
        companyService.updateReviewInCompany(reviewDto);
    }
}
