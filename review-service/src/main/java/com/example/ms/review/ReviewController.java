package com.example.ms.review;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody Review review) {

        Review savedReview = reviewService.addReview(review);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getReviews() {
       return new ResponseEntity<>(reviewService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable long id) throws ReviewNotFoundException {
        Review review = reviewService.getReviewById(id);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @GetMapping(params = {"companyId"})
    public ResponseEntity<?> getReviewByCompanyId(@RequestParam long companyId) throws ReviewNotFoundException {
        List<Review> review = reviewService.getReviewByCompanyId(companyId);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReviewById(@PathVariable long id) throws ReviewNotFoundException {
        boolean deleted = reviewService.deleteJobById(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
}
