package com.enosh.couponmongo.repository;

import com.enosh.couponmongo.model.Company;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends MongoRepository<Company, ObjectId> {

    Optional<Company> findByEmail(String email);
}
