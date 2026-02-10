package com.example.skinsaathi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class AddressResponse {

    private Long id;
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
