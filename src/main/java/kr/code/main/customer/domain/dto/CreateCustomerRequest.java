package kr.code.main.customer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequest {

    private String name;
    private int gender;
    private String birth;
    private String address;
    private String postCode;
    private String email;
    private String phone;
    private String company;
    private int position;
    private int department;

}
