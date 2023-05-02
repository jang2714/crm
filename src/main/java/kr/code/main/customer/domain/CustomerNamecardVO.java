package kr.code.main.customer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerNamecardVO {

    private String uid;
    private String name;
    private String address;
    private String postCode;
    private String email;
    private String phone;
    private String company;
    private String position;
    private String department;

}
