package com.enosh.couponmongo.service;

import com.enosh.couponmongo.model.Company;
import com.enosh.couponmongo.model.Coupon;
import com.enosh.couponmongo.model.CouponType;
import com.enosh.couponmongo.repository.CompanyRepository;
import com.enosh.couponmongo.repository.CouponRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@AllArgsConstructor
@Service
public class DataLoader implements CommandLineRunner {

    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;

    private Company initialCompany(String email, String password, String name) {
        Company company = Company.builder()
                .email(email)
                .password(password)
                .name(name)
                .coupons(new ArrayList<>())
                .build();

        return companyRepository
                .findByEmail(company.getEmail())
                .orElseGet(() -> companyRepository.insert(company));
    }

    public Coupon initialCoupon(String title, double price) {
        Coupon coupon = Coupon.builder()
                .amount(100)
                .title(title)
                .price(price)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusYears(1))
                .type(CouponType.FOOD)
                .build();

        return couponRepository.findByTitle(title)
                .orElseGet(() -> couponRepository.insert(coupon));
    }



    @Override
    public void run(String... args) throws Exception {

        Company company = initialCompany(
                "venga@vking.com",
                "vk123",
                "venga"
        );

        Coupon coupon = initialCoupon("" +
                "Food is cool",
                12.90
        );

        System.out.println(company);
        System.out.println(coupon);


        // print afterSave : returned company
    }
}
