package kr.code.main.customer.controller;

import kr.code.main.customer.domain.CustomerNamecardVO;
import kr.code.main.customer.domain.CustomerVO;
import kr.code.main.customer.domain.dto.CreateRequestDTO;
import kr.code.main.customer.service.CustomerService;
import kr.code.main.utils.UploadFileUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
        List<CustomerNamecardVO> list = customerService.getAllCustomers(1, 8);
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

    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<String> createCustomer(CreateRequestDTO customerReq,
                                                 HttpServletRequest req,
                                                 HttpServletResponse res) throws IOException {
        CustomerVO customer = null;
        HttpStatus httpStatus;
        boolean result = false;

        try{
            customer = customerService.findByName(customerReq.getName());
            if(customer == null) {
                //result = customerService.createCustomer(customerReq);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        httpStatus = result ? HttpStatus.OK : HttpStatus.CONFLICT;
        String msg = result ? customer.getCustomerUid() : null;

        return new ResponseEntity<>(msg, httpStatus);
    }


    // 게시글 파일 업로드
    @RequestMapping(value = "/file/upload", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> uploadFile(MultipartFile file, HttpServletRequest request) {
        ResponseEntity<String> entity = null;
        try {
            String savedFilePath = UploadFileUtils.uploadFile(file, request);
            entity = new ResponseEntity<>(savedFilePath, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    // 게시글 첨부파일 출력
    @RequestMapping(value = "/file/display", method = RequestMethod.GET)
    public ResponseEntity<byte[]> displayFile(String fileName, HttpServletRequest request) throws Exception {

        HttpHeaders httpHeaders = UploadFileUtils.getHttpHeaders(fileName); // Http 헤더 설정 가져오기
        String rootPath = UploadFileUtils.getRootPath(fileName, request); // 업로드 기본경로 경로

        ResponseEntity<byte[]> entity = null;

        // 파일데이터, HttpHeader 전송
        try (InputStream inputStream = new FileInputStream(rootPath + fileName)) {
            entity = new ResponseEntity<>(IOUtils.toByteArray(inputStream), httpHeaders, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    // 게시글 파일 삭제 : 게시글 작성 페이지
    @RequestMapping(value = "/file/delete", method = RequestMethod.POST)
    public ResponseEntity<String> deleteFile(String fileName, HttpServletRequest request) {
        ResponseEntity<String> entity = null;

        try {
            UploadFileUtils.deleteFile(fileName, request);
            entity = new ResponseEntity<>("DELETED", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return entity;
    }
}
