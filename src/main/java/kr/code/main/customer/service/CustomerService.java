package kr.code.main.customer.service;

import kr.code.main.common.File.service.FileService;
import kr.code.main.customer.domain.CustomerNamecardVO;
import kr.code.main.customer.domain.CustomerTagVO;
import kr.code.main.customer.domain.CustomerVO;
import kr.code.main.customer.domain.dto.CreateRequestDTO;
import kr.code.main.customer.domain.dto.UpdateRequestDTO;
import kr.code.main.customer.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerMapper customerMapper;
    private final FileService fileService;

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

        String completeAddress = "no address";
        if (customerReq.getAddress() != null) {
            completeAddress = customerReq.getAddress();
        }
        if (customerReq.getAddress2() != null) {
            completeAddress += " " + customerReq.getAddress2();
        }

        // 고객 정보 생성
        CustomerVO newCustomer = CustomerVO.builder()
                .customerUid(UUID.randomUUID().toString())
                .customerName(customerReq.getName())
                .customerGender(customerReq.getGender())
                .customerBirth(customerReq.getBirth())
                .address(completeAddress)
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

    @Transactional
    public CustomerVO updateCustomerInfo(UpdateRequestDTO customerReq) {

        int savedFileCnt = 0;
        String[] files = customerReq.getFiles();
        if ( files!= null && files.length > 0 ) {

            fileService.RemoveAll(customerReq.getUid());
            savedFileCnt = fileService.AddManagedFile(customerReq.getUid(), files, customerReq.getFileCnt());

/*            // 일일히 비교하는 것은 비효율적.
            List<String> alreadyHasFiles = fileService.GetManagedFileList(customerReq.getUid());
            files = Arrays.stream(files).filter( file -> {
                String fullpath = file.substring( file.lastIndexOf("=") + 1 );
                String filepath = fullpath.substring(0, 12);
                String filename = fullpath.substring(12);
                String diffVal = filepath + (MediaUtils.getMediaType(filename) != null ? "s_" : "") + filename;
                return !alreadyHasFiles.contains(diffVal);
            } ).toArray(String[]::new);

            if (files.length > 0) {
                savedFileCnt = fileService.AddManagedFile(customerReq.getUid(), files, customerReq.getFileCnt());
            }
*/
        }

        String completeAddress = "no address";
        if (customerReq.getAddress() != null) {
            completeAddress = customerReq.getAddress();
        }
        if (customerReq.getAddress2() != null) {
            completeAddress += " " + customerReq.getAddress2();
        }

        // 고객 정보 생성
        CustomerVO customer = CustomerVO.builder()
                .customerUid(customerReq.getUid())
                .customerName(customerReq.getName())
                .customerGender(customerReq.getGender())
                .customerBirth(customerReq.getBirth())
                .address(completeAddress)
                .postcode(customerReq.getPostcode())
                .customerEmail(customerReq.getEmail())
                .phoneNumber(customerReq.getPhone())
                .companyName(customerReq.getCompany())
                .posCode(customerReq.getPosition())
                .deptCode(customerReq.getDepartment())
                .attachedCnt(savedFileCnt)
                .build();

        int result = customerMapper.updateCustomer(customer);
        if (result > 0) {
            System.out.println("고객 정보 수정 완료 -> " + customer.getCustomerName() + " : " + customer.getCustomerUid());
        } else {
            System.out.println("고객 정보 수정 실패!");

            // throws RuntimeException
        }

        return customer;
    }
}
