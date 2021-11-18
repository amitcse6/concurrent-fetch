package com.ds.concurrent.fetch.dto.sc;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ScannedObject implements Serializable {
    private static final long serialVersionUID = -5616176897013108345L;
    private String scannedName;
    private String scannedMsisdn;
    private String scannedDOB;
    private String scannedIdNum;
    private String scannedCountry;
    private String scannedIdType;
}
