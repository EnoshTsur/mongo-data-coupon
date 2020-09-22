package com.enosh.couponmongo.service;

import com.enosh.couponmongo.model.Company;
import com.enosh.couponmongo.model.Coupon;
import com.enosh.couponmongo.model.CouponOwner;
import com.enosh.couponmongo.model.Customer;
import com.enosh.couponmongo.repository.CompanyRepository;
import com.enosh.couponmongo.repository.CouponRepository;
import com.enosh.couponmongo.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.enosh.couponmongo.common.CouponUtils.*;
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
                .firstName(firstName)
                .lastName(lastName)
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

    private <T> Function<String, String> add(T t) {
        Function<String, String> addT = str -> str + t;
        return addT;
    }
    // str -> str
    //  str -> str + 67

    @Override
    public void run(String... args) throws Exception {

        Function<String, String> addHi = str -> str + " hi";

        List<String> collect = Stream.of("adfsdf", "Sdfdsf", "adfsdf", "sdfsdf")
                .filter(x -> x.startsWith("a"))
                .map(addHi)
                .collect(Collectors.toList());

        Company company = initialCompany(
                "teva@climg.com",
                "v10",
                "climbing"
        );

        Coupon coupon = initialCoupon(
                "Food is awesome",
                120.90
        );

        Customer customer = initialCustomer("Iftach", "Leah");
        System.out.println(customer);

        BiFunction<Coupon, Company, Coupon> companyConnect = connectOwnerToCoupon(companyRepository);
        BiFunction<Coupon, Customer, Coupon> customerConnect = connectOwnerToCoupon(customerRepository);


        // (coupon , company) -> (repository) -> x
        // (repository) -> (coupon , owner) -> x
        //


        System.out.println(company);

        // print afterSave : returned company

    }


    private <T extends CouponOwner> BiFunction<Coupon, T, Coupon> connectOwnerToCoupon(
            MongoRepository<T, ObjectId> repository
    ){
        BiFunction<Coupon, T, Supplier<Coupon>> createFromExists = createFromExistList(repository);
        BiFunction<Coupon, T, Supplier<Coupon>> createFromNull = createFromNullishList(repository);

        return (coupon, owner) -> Optional.ofNullable(owner.getCoupons())
                .map(coupons -> coupons
                        .stream()
                        .filter(couponById(coupon.getId()))
                        .findFirst()
                        .orElseGet(createFromExists.apply(coupon, owner))
                ).orElseGet(createFromNull.apply(coupon, owner));
    }


    private Predicate<Coupon> filterByObjectId(Coupon original) {
        return currentCoupon -> currentCoupon.getId().equals(original.getId());
    }


    // (coupon, owner) -> (repository) -> () -> x

    private <T extends CouponOwner> BiFunction<Coupon, T, Supplier<Coupon>> createFromExistList(
            MongoRepository<T, ObjectId> repository
    ) {
        return (coupon, owner) -> () -> {
            owner.setCoupons(
                    Stream.concat(
                            owner.getCoupons().stream(),
                            Stream.of(coupon)
                    )
                            .collect(Collectors.toList())
            );
            repository.save(owner);
            return coupon;
        };
    }

    private <T extends CouponOwner> BiFunction<Coupon, T, Supplier<Coupon>> createFromNullishList(
            MongoRepository<T, ObjectId> repository
    ) {
        return (coupon, owner) -> () -> {
            owner.setCoupons(List.of(coupon));
            repository.save(owner);
            return coupon;
        };
    }
}




class Main {

    // Not that generic... :(
    private static BiFunction<String, String, String> add =
            (original, addition) -> original + addition;

    // Bad...   :(
    private static Function<String, String> addHi =
            original -> add.apply(original, "hi");

    // Amazing!
    private static <T> Function<String,  String> add(T addition) {
        return original -> original + " " + addition;
    }

    // filter(Predicate<String>
    // filter(str -> str.startWith("a")
    // Predicate<String> startsWith(String subText)

    private static Predicate<String> startsWith(String subText) {
        return original -> original.startsWith(subText);
    }

    public static void main(String[] args) {
        List<String> greetings = Stream.of("Hi", "Bye", "Hello", "Whatsup?")
                .filter(startsWith("dfgdfg"))
                .collect(Collectors.toList());

        System.out.println(greetings);
    }
}












