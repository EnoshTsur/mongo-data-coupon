package com.enosh.couponmongo.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Document(collection = "Company")
public class Company implements CouponOwner{

    @Id
    private ObjectId id;

    private String name;

    @Indexed(unique = true)
    private String email;
    private String password;
    private List<Coupon> coupons;

    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = new ArrayList<>();
    }
}
