package com.ds.concurrent.fetch.dto.sc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Document implements Serializable {
    private static final long serialVersionUID = -5616176897013108345L;
    private String type;
    private String number;
}

