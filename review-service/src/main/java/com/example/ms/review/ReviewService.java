package com.example.ms.review;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMessageProducer reviewMessageProducer;

    private ModelMapper modelMapper = new ModelMapper();

    public Review addReview(Review review) {

        Review savedReview = reviewRepository.save(review);
        reviewMessageProducer.sendMessage(modelMapper.map(savedReview, ReviewMessage.class));
        return savedReview;
    }

    public List<Review> getAll() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews;
    }

    public Review getReviewById(long id) throws ReviewNotFoundException {

        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if(reviewOptional.isPresent()) {
            return reviewOptional.get();
        }
        else throw new ReviewNotFoundException(MessageFormat.format("Review with id {0} not found", id ));
    }

    public boolean deleteJobById(long id) throws ReviewNotFoundException {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if(reviewOptional.isPresent()) {
            Review job = reviewOptional.get();
            reviewRepository.delete(job);
            return true;
        }
        else throw new ReviewNotFoundException(MessageFormat.format("Review with id {0} not found", id ));
    }
}
