package com.enosh.couponmongo.repository;

import com.enosh.couponmongo.model.Company;
import com.enosh.couponmongo.model.Customer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, ObjectId> {

    Optional<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}
