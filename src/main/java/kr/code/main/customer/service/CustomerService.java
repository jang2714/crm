package kr.code.main.customer.service;

import kr.code.main.common.File.service.FileService;
import kr.code.main.common.comment.domain.Comment;
import kr.code.main.common.comment.domain.CommentVO;
import kr.code.main.common.comment.domain.dto.CommentDTO;
import kr.code.main.common.comment.repository.CommentRepository;
import kr.code.main.common.exception.AppException;
import kr.code.main.common.exception.ErrorCode;
import kr.code.main.customer.domain.CustomerNamecardVO;
import kr.code.main.customer.domain.CustomerTagVO;
import kr.code.main.customer.domain.CustomerVO;
import kr.code.main.customer.domain.dto.CreateRequestDTO;
import kr.code.main.customer.domain.dto.UpdateRequestDTO;
import kr.code.main.customer.mapper.CustomerMapper;
import kr.code.main.user.domain.entity.UserEntity;
import kr.code.main.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerMapper customerMapper;
    private final FileService fileService;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

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

    public int countCustomerBySearchKey(String searchKey) {
        return customerMapper.countCustomerBySearchKey(searchKey);
    }

    public List<CustomerNamecardVO> findCustomerBySearchKey(String search, int startRow, int count) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", search);
        params.put("current", startRow);
        params.put("count", count);

        return customerMapper.findCustomerBySearchKey(params);
    }

    public Optional<CustomerVO> findByName(String name) {
        return customerMapper.findByName(name);
    }

    public Optional<CustomerVO> findByUid(String customerUid) {
        return customerMapper.findByUid(customerUid);
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
            throw new AppException(ErrorCode.INVALID_REQUEST, "알수없는 이유로 고개정보 등록이 실패하였습니다. 다시 시도해주세요.");
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

            throw new AppException(ErrorCode.INVALID_REQUEST, "고객 정보를 수정 할 수 없습니다.");
        }

        return customer;
    }

    public List<CommentVO> getCommentByCustomerUid(String customerUid) {

        List<Comment> list = commentRepository.findAllByCustomerUidOrderByCreateDateDesc(customerUid);

        return list.stream().map( vo -> { return new CommentVO(
                vo.getWriter().getUserName(), vo.getTitle(), vo.getComment(), vo.getCreateDate()
        );}).toList();
    }

    @Transactional
    public List<CommentVO> registerComment(CommentDTO commentReq) {

        UserEntity user = userRepository.findByUserUid(commentReq.getWriterUid())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, "코멘트 작성자가 존재하지 않습니다."));

        Comment newComment = Comment.builder()
                .customerUid(commentReq.getCustomerUid())
                .writer(user)
                .title(commentReq.getCommentTitle())
                .comment(commentReq.getContents())
                .build();

        commentRepository.save(newComment);

        return getCommentByCustomerUid(commentReq.getCustomerUid());
    }

}
