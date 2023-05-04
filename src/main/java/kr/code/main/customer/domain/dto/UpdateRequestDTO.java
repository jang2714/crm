package kr.code.main.customer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequestDTO {

    private String uid;
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

    private String[] files;
    private int fileCnt;

    public void setFiles(String[] files) {
        this.files = files;
        setFileCnt(files.length);
    }

    private void setFileCnt(int fileCnt) {
        this.fileCnt = fileCnt;
    }
}
