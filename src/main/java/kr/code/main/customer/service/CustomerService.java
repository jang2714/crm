package kr.code.main.customer.service;

import kr.code.main.common.department.domain.DepartmentVO;
import kr.code.main.common.position.domain.PositionVO;
import kr.code.main.customer.domain.*;
import kr.code.main.customer.domain.dto.CreateRequestDTO;
import kr.code.main.customer.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerMapper customerMapper;

    public int getTotalCustomerCount() {
        return customerMapper.getTotalCustomerCount(new HashMap<String, Object>());
    }

    public List<CustomerNamecardVO>  getAllCustomers(int start, int showCount) {

        Map<String, Object> params = new HashMap<>();
        params.put("current", start);
        params.put("count", showCount);

        return customerMapper.getAllCustomer(params);
    }

    public List<CustomerNamecardVO> getCustomersByTag(String tagTitle) {
        Map<String, Object> params = new HashMap<>();
        params.put("tagTitle", tagTitle);

        return customerMapper.getCustomersByTag(params);
    }

    public CustomerVO findByName(String name) {
        return customerMapper.findByName(name)
                .orElse(null);
    }

    public CustomerVO findByUid(String customerUid) {
        return customerMapper.findByUid(customerUid)
                .orElse(null);
    }

    public List<CustomerTagVO> getAllCustomerTagById(String customerId) {
        return customerMapper.getCustomerTagsById(customerId);
    }

    public int insertCustomerAndTag(Map<String, Object> params) {
        return customerMapper.insertCustomerAndTag(params);
    }

    public CustomerVO createCustomer(CreateRequestDTO customerReq) {

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
        if (result > 0) {
            System.out.println("고객 정보 생성 완료 -> " + newCustomer.getCustomerName() + " : " + newCustomer.getCustomerUid());
        } else {
            System.out.println("고객 정보 생성 실패!");

            // throws RuntimeException
        }

        return newCustomer;
    }

    public List<PositionVO> getPositionMap() {
        return customerMapper.getPositionMap();
    }

    public List<DepartmentVO> getDepartmentList() {
        return customerMapper.getDepartmentList();
    }
}
