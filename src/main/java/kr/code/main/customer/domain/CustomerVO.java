package kr.code.main.customer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CustomerVO {

    private String customerUid;
    private String customerName;
    private int customerGender;
    private String customerBirth;
    private String address;
    private String postcode;
    private String customerEmail;
    private String phoneNumber;
    private String companyName;
    private int posCode;
    private int deptCode;

}
