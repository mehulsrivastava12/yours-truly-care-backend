package com.example.skinsaathi.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class AddressRequest {

    private String pincode;
    private String houseNo;
    private String area;
    private String city;
    private String state;
    private String country;
    private String contactName;
    private String phoneNumber;
    private Boolean defaultAddress;
}
