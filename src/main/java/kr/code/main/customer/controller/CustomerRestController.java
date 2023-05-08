package kr.code.main.customer.controller;

import kr.code.main.common.File.service.FileService;
import kr.code.main.common.comment.domain.CommentVO;
import kr.code.main.common.comment.domain.dto.CommentDTO;
import kr.code.main.common.tag.domain.Tag;
import kr.code.main.common.tag.service.TagService;
import kr.code.main.customer.domain.CustomerNamecardVO;
import kr.code.main.customer.domain.CustomerVO;
import kr.code.main.customer.domain.dto.CreateRequestDTO;
import kr.code.main.customer.domain.dto.UpdateRequestDTO;
import kr.code.main.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerRestController {

    private final CustomerService customerService;
    private final TagService tagService;
    private final FileService fileService;

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

    @Transactional
    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<String> createCustomer(CreateRequestDTO customerReq,
                                                 HttpServletRequest req,
                                                 HttpServletResponse res) throws IOException {
        CustomerVO customer = null;

        try{
            // 고객 정보 동록
            customer = customerService.createCustomer(customerReq);

            // tag 정보를 생성 및 등록
            List<Tag> tags = tagService.saveTagsFromCustomerInfo(customer);

            // 고객 정보 와 tag의 연관관계 등록 (N vs N)
            String customerId = customer.getCustomerUid();
            tags.forEach(tag -> {
                Map<String, Object> linkParams = new HashMap<>();
                linkParams.put("customerId", customerId);
                linkParams.put("tagId", tag.getTagId());

                customerService.insertCustomerAndTag(linkParams);
            });

        } catch(Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(customer.getCustomerUid());
    }

    @PostMapping(value = "/update", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<String> updateCustomer(UpdateRequestDTO customerReq,
                                                 HttpServletRequest req,
                                                 HttpServletResponse res) throws IOException {

        final String uid = customerReq.getUid();

        CustomerVO customer = customerService.updateCustomerInfo(customerReq);

        return ResponseEntity.ok(customer.getCustomerUid());
    }

    @GetMapping(value="attached")
    public ResponseEntity<String[]> retrieveFiles(@RequestParam(name="customerUid", required=false) String customerUid) {
        List<String> hasFiles = fileService.GetManagedFileList(customerUid);

        String[] toJs = hasFiles.toArray(new String[0]);

        return ResponseEntity.ok(toJs);
    }

    @GetMapping(value="comment")
    public ResponseEntity<List<CommentVO>> getCommentList(@RequestParam(name="customerUid") String customerUid) {

        List<CommentVO> list = customerService.getCommentByCustomerUid(customerUid);

        return ResponseEntity.ok(list);
    }

    @PostMapping(value="comment")
    public ResponseEntity<List<CommentVO>> addComment(@RequestBody CommentDTO commentReq) {

        return ResponseEntity.ok(customerService.registerComment(commentReq));
    }
}
