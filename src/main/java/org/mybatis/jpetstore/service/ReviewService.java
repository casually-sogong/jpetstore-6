package org.mybatis.jpetstore.service;

import org.mybatis.jpetstore.domain.*;
import org.mybatis.jpetstore.mapper.ProductMapper;
import org.mybatis.jpetstore.mapper.ReviewMapper;
import org.mybatis.jpetstore.mapper.ReviewRatingMapper;
import org.mybatis.jpetstore.mapper.SequenceMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    private final ReviewMapper reviewMapper;
    private final ReviewRatingMapper reviewRatingMapper;
    private final ProductMapper productMapper;
    private final SequenceMapper sequenceMapper;

    ReviewService(ReviewMapper reviewMapper, ReviewRatingMapper reviewRatingMapper, ProductMapper productMapper, SequenceMapper sequenceMapper) {
        this.reviewMapper = reviewMapper;
        this.reviewRatingMapper = reviewRatingMapper;
        this.productMapper = productMapper;
        this.sequenceMapper = sequenceMapper;
    }

    public List<Review> getReviews() {
        return this.reviewMapper.getReviews();
    }

    public Review getReviewById(String id) {
        return this.reviewMapper.getReviewById(id);
    }

    public List<ReviewRating> getReviewRatingById(String id) {
        return this.reviewRatingMapper.getReviewRatingByReviewId(id);
    }

    public Product getProduct(String productId) {
        return productMapper.getProduct(productId);
    }


    public void deleteReview(String id) {
        reviewRatingMapper.deleteReviewRatingByReviewId(id);
        reviewMapper.deleteReviewById(id);
    }
    public Product getProductById(String id) {
        Item item = this.reviewMapper.getItemById(id);
        return this.reviewMapper.getProductById(item.getProductId());
    }

    public Review insertReview(Review review) {
        String reviewId = "RV-"+getNextId("reviewnum");
        review.setReviewId(reviewId);
        reviewMapper.insertReview(review);
        return review;
    }
    public int getNextId(String name) {
        Sequence sequence = sequenceMapper.getSequence(new Sequence(name, -1));
        if (sequence == null) {
            throw new RuntimeException(
                    "Error: A null sequence was returned from the database (could not get next " + name + " sequence).");
        }
        Sequence parameterObject = new Sequence(name, sequence.getNextId() + 1);
        sequenceMapper.updateSequence(parameterObject);
        return sequence.getNextId();
    }

    public void insertReviewRating(List<ReviewRating> ratingList) {
        for (ReviewRating rr : ratingList) {
            reviewMapper.insertReviewRating(rr);
        }
    }


    public List<Review> getReviewList(String productId) {return reviewMapper.getReivewListByProductId(productId);  }



    public Map<String, Double> getRatingMapByCategory(String categoryId){
        Map<String, Double> result = new HashMap<>();
        List<Product> products = productMapper.getProductListByCategory(categoryId);
        for(Product product:products){
            String productId = product.getProductId();
            Double avg = getAverageRatingByProductId(productId);
            if (avg == null) continue;
            result.put(productId, Math.round(getAverageRatingByProductId(productId)*100)/100.0);
        }
        return result;
    }

    public Double getAverageRatingByProductId(String productId){
        List<Integer> ratings = new ArrayList<>();
        List<Review> reviews = reviewMapper.getReivewListByProductId(productId);
        if(reviews.isEmpty()) return null;
        for(Review review:reviews){
            String reviewId = review.getReviewId();
            reviewRatingMapper.getReviewRatingByReviewId(reviewId).forEach(reviewRating -> ratings.add(reviewRating.getRating()));
        }
        return ratings.stream().mapToDouble(num -> (double) num).sum() / ratings.size();
    }

}
