package com.ds.concurrent.fetch.dto.sc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseResult implements Serializable {
    private static final long serialVersionUID = -5616176897013108345L;
    private String id;
    private ScannedObject scannedObject;
    private Boolean matches;
    private List<MatchResult> matchResults = new ArrayList<>();
    private String requestId;
    private ZonedDateTime createdDate;
    private Boolean whiteListStatus;
    private String sourceType;
    private String gender;
    private String screeningResult;
    private String version;
    private String whiteListId;
    private Double threshold;
    private String whiteListScanStatus;
    private Boolean afterScanResult;
    private String fullScreenedData;
    private ScreeningProcess screeningProcess;
    private List<AmlCustomer> customers;
}
