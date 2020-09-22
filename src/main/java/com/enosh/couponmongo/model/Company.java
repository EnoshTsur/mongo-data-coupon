package com.enosh.couponmongo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "Company")
public class Company {

    @Id
    private ObjectId id;

    private String name;
    private String email;
    private String password;


}
