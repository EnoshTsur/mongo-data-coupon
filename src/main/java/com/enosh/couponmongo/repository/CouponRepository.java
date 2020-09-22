package com.enosh.couponmongo.repository;

import com.enosh.couponmongo.model.Coupon;
import com.enosh.couponmongo.model.Customer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends MongoRepository<Coupon, ObjectId> {

    Optional<Coupon> findByTitle(String title);
}
