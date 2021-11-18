package com.ds.concurrent.fetch.dto.sc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchResult implements Serializable {
    private static final long serialVersionUID = -5616176897013108345L;
    private String dataid;
    private String name;
    private String firstName;
    private String lastName;
    private String fatherName;
    private String motherName;
    private String gender;
    private String accountNumber;
    private String accountType;
    private List<Object> program = new ArrayList<>();
    private String nationality;
    private String citizenship;
    private List<Object> dateOfBirth = new ArrayList<>();
    private String placeofBirth;
    private List<Object> otherNames = new ArrayList<>();
    private List<Address> address = new ArrayList<>();
    private List<Document> documents = new ArrayList<>();
    private String otherInformation;
    private String sourceSDN;
    private Double matchRate;
}
