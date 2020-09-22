package com.enosh.couponmongo.service;

import com.enosh.couponmongo.model.Company;
import com.enosh.couponmongo.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DataLoader implements CommandLineRunner {

    private final CompanyRepository companyRepository;

    @Override
    public void run(String... args) throws Exception {
        // create company
        Company company = Company.builder()
                .email("venga@vking.com")
                .password("vk123")
                .name("venga")
                .build();

        // if does not exists by email -> create : return

        // print afterSave : returned company
    }
}
