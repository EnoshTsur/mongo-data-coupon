package com.enosh.couponmongo.model;

import java.util.List;

public interface CouponOwner {

    List<Coupon> getCoupons();

    void setCoupons(List<Coupon> coupons);
}
