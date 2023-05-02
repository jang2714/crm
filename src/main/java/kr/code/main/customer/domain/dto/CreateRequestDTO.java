package kr.code.main.customer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRequestDTO {

    private String name;
    private int gender;
    private String birth;
    private String address;
    private String address2;
    private String postcode;
    private String email;
    private String phone;
    private String company;
    private int position;
    private int department;

}
