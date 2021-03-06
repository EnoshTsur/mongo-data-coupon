package com.enosh.couponmongo.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder

@Getter
@Setter
@ToString
@Document(collection = "Customer")
public class Customer implements CouponOwner{

    @Id
    private ObjectId id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private List<Coupon> coupons;

    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
