package com.enosh.couponmongo.service;

import com.enosh.couponmongo.model.Company;
import com.enosh.couponmongo.model.Coupon;
import com.enosh.couponmongo.model.Customer;
import com.enosh.couponmongo.repository.CompanyRepository;
import com.enosh.couponmongo.repository.CouponRepository;
import com.enosh.couponmongo.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.enosh.couponmongo.model.CouponType.*;

@AllArgsConstructor
@Service
public class DataLoader implements CommandLineRunner {

    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;

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

    private Customer initialCustomer(String firstName, String lastName) {
        Customer customer = Customer.builder()
                .email(firstName + lastName + "@gmail.com")
                .password(firstName + lastName)
                .coupons(new ArrayList<>())
                .build();

        return customerRepository
                .findByFirstNameAndLastName(firstName, lastName)
                .orElseGet(() -> customerRepository.insert(customer));
    }

    private Coupon initialCoupon(String title, double price) {
        Coupon coupon = Coupon.builder()
                .amount(100)
                .title(title)
                .price(price)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusYears(1))
                .type(FOOD)
                .build();

        return couponRepository.findByTitle(title)
                .orElseGet(() -> couponRepository.insert(coupon));
    }


    @Override
    public void run(String... args) throws Exception {

        Company company = initialCompany(
                "teva@climg.com",
                "v10",
                "climbing"
        );

        Coupon coupon = initialCoupon(
                "Food is awesome",
                120.90
        );

        Customer customer = initialCustomer("Avner", "Malachi");
        System.out.println(customer);

        connectCompanyCoupon(coupon, company);


        System.out.println(company);

        // print afterSave : returned company
    }

    private Coupon connectCompanyCoupon(Coupon coupon, Company company) {
        return Optional.ofNullable(company.getCoupons())
                .map(coupons -> coupons
                        .stream()
                        .filter(filterByObjectId(coupon))
                        .findFirst()
                        .orElseGet(createFromExistList(coupon, company))
                ).orElseGet(createFromNullishList(coupon, company));
    }

    private Predicate<Coupon> filterByObjectId(Coupon original) {
        return currentCoupon -> currentCoupon.getId().equals(original.getId());
    }

    private Supplier<Coupon> createFromExistList(Coupon coupon, Company company) {
        return () -> {
            company.setCoupons(
                    Stream.concat(
                            company.getCoupons().stream(),
                            Stream.of(coupon)
                    )
                            .collect(Collectors.toList())
            );
            companyRepository.save(company);
            return coupon;
        };
    }

    private Supplier<Coupon> createFromNullishList(Coupon coupon, Company company) {
        return () -> {
            company.setCoupons(List.of(coupon));
            companyRepository.save(company);
            return coupon;
        };
    }
}
