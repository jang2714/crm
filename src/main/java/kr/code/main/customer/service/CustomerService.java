package kr.code.main.customer.service;

import kr.code.main.customer.domain.CustomerNamecardVO;
import kr.code.main.customer.domain.CustomerVO;
import kr.code.main.customer.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
