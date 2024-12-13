package com.example.messaging.review;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMessageProducer reviewMessageProducer;

    private ModelMapper modelMapper = new ModelMapper();

    public ReviewDto addReview(ReviewDto reviewDto) {
        Review review = new Review();
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setCompanyId(reviewDto.getCompanyId());

        Review savedReview = reviewRepository.save(review);

        reviewDto.setId(savedReview.getId());
        reviewMessageProducer.sendMessage(reviewDto);

        return reviewDto;
    }

    public List<ReviewDto> getAll() {

        List<ReviewDto> reviewDtos = new ArrayList<>();
        List<Review> reviews = reviewRepository.findAll();
        for(Review review: reviews) {
            reviewDtos.add(modelMapper.map(review, ReviewDto.class));
        }
        return reviewDtos;
    }
}
