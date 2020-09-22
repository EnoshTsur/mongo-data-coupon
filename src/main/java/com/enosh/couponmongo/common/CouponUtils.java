package com.enosh.couponmongo.common;

import com.enosh.couponmongo.model.Company;
import com.enosh.couponmongo.model.Coupon;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public interface CouponUtils {

    private static int nine() {
        return 9;
    }

    static Predicate<Coupon> couponById(ObjectId id) {
        return coupon -> coupon.getId().equals(id);
    }

}
