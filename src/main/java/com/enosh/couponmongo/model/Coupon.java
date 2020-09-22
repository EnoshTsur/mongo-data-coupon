package com.enosh.couponmongo.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Document(collection = "Coupon")
public class Coupon {

    @Id
    private ObjectId id;

//    private ObjectId companyId;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String image;
    private int amount;
    private double price;
    private CouponType type;

    public Coupon(String title, LocalDate startDate, LocalDate endDate, String description, String image, int amount, double price, CouponType type) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.image = image;
        this.amount = amount;
        this.price = price;
        this.type = type;
    }
}
