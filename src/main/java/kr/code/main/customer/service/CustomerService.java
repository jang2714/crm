package kr.code.main.customer.service;

import kr.code.main.customer.domain.CustomerNamecardVO;
import kr.code.main.customer.domain.CustomerVO;
import kr.code.main.customer.domain.DepartmentVO;
import kr.code.main.customer.domain.PositionVO;
import kr.code.main.customer.domain.dto.CreateRequestDTO;
import kr.code.main.customer.mapper.CustomerMapper;
import kr.code.main.utils.UploadFileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerMapper customerMapper;

    public int getTotalCustomerCount() {
        return customerMapper.getTotalCustomerCount(new HashMap<String, Object>());
    }

    public List<CustomerNamecardVO>  getAllCustomers(int startPage, int showCount) {

        Map<String, Object> params = new HashMap<>();
        params.put("current", startPage);
        params.put("count", showCount);

        return customerMapper.getAllCustomer(params);
    }

    public CustomerVO findByName(String name) {
        return customerMapper.findByName(name)
                .orElse(null);
    }

    public CustomerVO findByUid(String customerUid) {
        return customerMapper.findByUid(customerUid)
                .orElse(null);
    }

    public boolean createCustomer(CreateRequestDTO customerReq) {

        // 고객 정보 생성
        CustomerVO newCustomer = CustomerVO.builder()
                .customerUid(UUID.randomUUID().toString())
                .customerName(customerReq.getName())
                .customerGender(customerReq.getGender())
                .customerBirth(customerReq.getBirth())
                .address(customerReq.getAddress() + " " + customerReq.getAddress2())
                .postcode(customerReq.getPostcode())
                .customerEmail(customerReq.getEmail())
                .phoneNumber(customerReq.getPhone())
                .companyName(customerReq.getCompany())
                .posCode(customerReq.getPosition())
                .deptCode(customerReq.getDepartment())
                .attachedCnt(0)
                .build();

        int result = customerMapper.createCustomer(newCustomer);
        return result > 0;
    }

    public List<PositionVO> getPositionMap() {
        return customerMapper.getPositionMap();
    }

    public List<DepartmentVO> getDepartmentList() {
        return customerMapper.getDepartmentList();
    }
}
