package kr.code.main.customer.controller;

import kr.code.main.common.File.service.FileService;
import kr.code.main.common.department.domain.DepartmentVO;
import kr.code.main.common.department.service.DepartmentService;
import kr.code.main.common.exception.AppException;
import kr.code.main.common.exception.ErrorCode;
import kr.code.main.common.position.domain.PositionVO;
import kr.code.main.common.position.service.PositionService;
import kr.code.main.customer.domain.CustomerNamecardVO;
import kr.code.main.customer.service.CustomerService;
import kr.code.main.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final PositionService positionService;
    private final DepartmentService departmentService;
    private final FileService fileService;

    @GetMapping("/create")
    public ModelAndView viewCreateCustomer(HttpServletRequest req) {

        ModelAndView mav = new ModelAndView("views/customer/createCustomer");

        List<PositionVO> positions = positionService.GetAllPosition();
        mav.addObject("positions", positions);

        List<DepartmentVO> departments = departmentService.GetAllDepartment();
        mav.addObject("departments", departments);

        return mav;
    }

    @GetMapping("/details")
    public ModelAndView viewDetailsCustomer(@RequestParam(value="uid", required = false) String customerUid) {
        ModelAndView mav = new ModelAndView("views/customer/detailCustomer");

        mav.addObject("customer", customerService.findByUid(customerUid)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, "고객 정보를 찾을 수 없습니다.")));
        mav.addObject("positions", positionService.GetAllPosition());
        mav.addObject("departments", departmentService.GetAllDepartment());

        return mav;
    }

    @GetMapping("/list")
    public ModelAndView viewCustomerList(HttpSession session) {

        ModelAndView mav = new ModelAndView("views/customer/listupCustomer");

        session.setAttribute("keyword", null);

        int totalCustomerCount = customerService.getTotalCustomerCount();
        int rowsPerPage = 8;
        int currentPage = 1;

        PaginationUtils pagination = new PaginationUtils(totalCustomerCount, rowsPerPage, currentPage);
        int startRow = pagination.getStartRow();
        int endRow = pagination.getEndRow();

        List<CustomerNamecardVO> list = customerService.getAllCustomers(startRow, rowsPerPage);

        attachObjectsToModel(currentPage, mav, totalCustomerCount, rowsPerPage, pagination, startRow, endRow, list);

        return mav;
    }

    private void attachObjectsToModel(@RequestParam(value = "currentPage", required = false) int currentPage, ModelAndView mav, int totalCustomerCount, int rowsPerPage, PaginationUtils pagination, int startRow, int endRow, List<CustomerNamecardVO> list) {
        mav.addObject("cardList", list);
        mav.addObject("totalCustomerCount", totalCustomerCount);
        mav.addObject("currentPage", currentPage);
        mav.addObject("startRow", startRow);
        mav.addObject("endRow", endRow);
        mav.addObject("countPerPage", rowsPerPage);
        mav.addObject("hasNextPage", pagination.hasNextPage() ? "true" : "false");
        mav.addObject("hasPrevPage", pagination.hasPreviousPage() ? "true" : "false");
    }

    @GetMapping("/search")
    public ModelAndView viewCustomerSearch(@RequestParam(value = "search", defaultValue = "") String search,
                                           @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                                           HttpSession session) {

        ModelAndView mav = new ModelAndView("views/customer/listupCustomer");

        final int COUNT_PER_PAGE = 8;

        // 검색어가 입력된 경우 세션에 저장
        if (search != null) {
            search.trim();
            if (!search.isEmpty()) {
                session.setAttribute("keyword", search);
            }
        }

        // 세션에서 검색어를 읽어옴
        String keyword = (String) session.getAttribute("keyword");
        // keyword로 서치하는 경우
        if (keyword!= null && !keyword.isEmpty()) {

            int totalCustomerCount = customerService.countCustomerBySearchKey(keyword);

            PaginationUtils pagination = new PaginationUtils(totalCustomerCount, COUNT_PER_PAGE, currentPage);
            int startRow = pagination.getStartRow();
            int endRow = pagination.getEndRow();
            List<CustomerNamecardVO> list = customerService.findCustomerBySearchKey(keyword, startRow, endRow);

            attachObjectsToModel(currentPage, mav, totalCustomerCount, COUNT_PER_PAGE, pagination, startRow, endRow, list);

        } else {
            // 서치 없이 목록을 출력 할때
            int totalCustomerCount = customerService.getTotalCustomerCount();

            PaginationUtils pagination = new PaginationUtils(totalCustomerCount, COUNT_PER_PAGE, currentPage);
            int startRow = pagination.getStartRow();
            int endRow = pagination.getEndRow();

            List<CustomerNamecardVO> list = customerService.getAllCustomers(startRow, COUNT_PER_PAGE);

            attachObjectsToModel(currentPage, mav, totalCustomerCount, COUNT_PER_PAGE, pagination, startRow, endRow, list);
        }

        return mav;
    }
}
