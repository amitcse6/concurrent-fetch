package com.ds.concurrent.fetch.dto.sc;

import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
public class AmlCustomer implements Serializable {
    private static final long serialVersionUID = -5616176897013108345L;
    private String id;
    private String name;
    private String gender;
    private String occupation;
    private String mothersName;
    private String fathersName;
    private String dob;
    private String photoIdType;
    private String photoIdNumber;
    private String accountWalletNumber;
    private String guardiansName;
    private String guardiansNid;
    private String nationality;
    private String identityStatus;
    private String accountStatus;
    private String stateTag;
    private String stateTagReason;
    private String stateTagDate;
    private String priority;
    private String permanentAddress;
    private String presentAddress;
    private String shopName;
    private String accountType;
    private String trustLevel;
    private String registeredDate;
    private String remarks;
    private String kycStatus;
    private String ecVerificationStatus;

    private String screeningType;
    private String fileId;
    private String hashCode;

    // default fields for both customer & entity
    private String photoIdExpiryDate;
    private String registrationChannel;
    private String registrationAccountNumber;
    private String screenedResult;
    private String bbCompliance;
    private String donotActive;
    private String scannedObject;
    private Integer countResult;
    private ZonedDateTime createdDate;
    private String reviewerId;
    private ZonedDateTime reviewerDate;
    private String reviewStatus;
    private String comments;
    private ZonedDateTime reviewDate;
    private String whiteListPassStatus; // remove from whitelist customer
    private Boolean screeningMatchResult;
    private String caseGenarationId;

}
