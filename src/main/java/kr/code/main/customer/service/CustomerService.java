package kr.code.main.customer.service;

import kr.code.main.customer.domain.CustomerNamecardVO;
import kr.code.main.customer.domain.CustomerVO;
import kr.code.main.customer.domain.dto.CreateCustomerRequest;
import kr.code.main.customer.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerMapper customerMapper;

    public List<CustomerNamecardVO>  getAllCustomers() {
        return customerMapper.getAllCustomer();
    }

    public CustomerVO findByName(String name) {
        Optional<CustomerVO> customer = customerMapper.findByName(name);
        if(customer.isPresent()) {
            return customer.get();
        }

        return null;
    }

    public boolean createCustomer(CreateCustomerRequest customerReq) {

        CustomerVO newCustomer = CustomerVO.builder()
                .customerUid(UUID.randomUUID().toString())
                .customerName(customerReq.getName())
                .customerGender(customerReq.getGender())
                .customerBirth(customerReq.getBirth())
                .address(customerReq.getAddress())
                .postcode(customerReq.getPostCode())
                .customerEmail(customerReq.getEmail())
                .phoneNumber(customerReq.getPhone())
                .companyName(customerReq.getCompany())
                .posCode(customerReq.getPosition())
                .deptCode(customerReq.getDepartment())
                .build();

        int result = customerMapper.createCustomer(newCustomer);
        return result > 0;
    }
}
