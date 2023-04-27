package kr.code.main.customer.controller;

import kr.code.main.customer.domain.CustomerNamecardVO;
import kr.code.main.customer.domain.CustomerVO;
import kr.code.main.customer.domain.dto.CreateCustomerRequest;
import kr.code.main.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerRestController {

    private final CustomerService customerService;

    @GetMapping("/namecards")
    public ResponseEntity<List<CustomerNamecardVO>> getCustomers() {
        List<CustomerNamecardVO> list = customerService.getAllCustomers();
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(list);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> findCustomer(@RequestParam(name="name", required=false) String name
                                             ) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus httpStatus;

        CustomerVO customer = customerService.findByName(name);
        if(customer != null) {
            resultMap.put("result", "SUCCESS");
            resultMap.put("customer", customer);
            httpStatus = HttpStatus.OK;
        } else {
            resultMap.put("result", "FAIL");
            resultMap.put("customer", null);
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(resultMap, httpStatus);
    }

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody CreateCustomerRequest request) {

        return ResponseEntity.ok().body("구현중");
    }
}
