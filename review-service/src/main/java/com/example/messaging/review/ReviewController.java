package com.example.messaging.review;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public void addReview(@RequestBody ReviewDto review) {
        reviewService.addReview(review);
    }

    @GetMapping
    public List<ReviewDto> getReviews() {
       return reviewService.getAll();
    }
}
