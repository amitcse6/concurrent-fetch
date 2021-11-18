package com.ds.concurrent.fetch.dto.sc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address implements Serializable {
    private static final long serialVersionUID = -5616176897013108345L;
    private String address;

    private String zipCode;

    private String city;

    private String state;

    private String country;

    private String presentAddress;

    private String permanentAddress;
}
