package kr.code.main.customer.controller;

import kr.code.main.customer.domain.CustomerNamecardVO;
import kr.code.main.customer.domain.CustomerVO;
import kr.code.main.common.department.domain.DepartmentVO;
import kr.code.main.common.position.domain.PositionVO;
import kr.code.main.customer.service.CustomerService;
import kr.code.main.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/create")
    public ModelAndView viewCreateCustomer() {

        ModelAndView mav = new ModelAndView("views/customer/createCustomer");

        List<PositionVO> positions = customerService.getPositionMap();
        mav.addObject("positions", positions);

        List<DepartmentVO> departments = customerService.getDepartmentList();
        mav.addObject("departments", departments);

        return mav;
    }

    @GetMapping("/details")
    public ModelAndView viewDetailsCustomer(@RequestParam(value="uid", required = false) String customerUid) {
        ModelAndView mav = new ModelAndView("views/customer/detailCustomer");

        CustomerVO customer = customerService.findByUid(customerUid);
        mav.addObject("customer", customer);

        List<PositionVO> positions = customerService.getPositionMap();
        mav.addObject("positions", positions);

        List<DepartmentVO> departments = customerService.getDepartmentList();
        mav.addObject("departments", departments);

        // file 처리 필요

        // comment 처리 필요

        return mav;
    }

    @GetMapping("/list")
    public ModelAndView viewCustomerList(@RequestParam(value = "currentPage", required = false) int currentPage) {

        ModelAndView mav = new ModelAndView("views/customer/listupCustomer");

        int totalCustomerCount = customerService.getTotalCustomerCount();
        int rowsPerPage = 8;

        PaginationUtils pagination = new PaginationUtils(totalCustomerCount, rowsPerPage, currentPage);
        int startRow = pagination.getStartRow();
        int endRow = pagination.getEndRow();

        List<CustomerNamecardVO> list = customerService.getAllCustomers(startRow, rowsPerPage);

        mav.addObject("cardList", list);
        mav.addObject("totalCustomerCount", totalCustomerCount);
        mav.addObject("currentPage", currentPage);
        mav.addObject("startRow", startRow);
        mav.addObject("endRow", endRow);
        mav.addObject("countPerPage", rowsPerPage);
        mav.addObject("hasNextPage", pagination.hasNextPage() ? "true" : "false");
        mav.addObject("hasPrevPage", pagination.hasPreviousPage() ? "true" : "false");

        return mav;
    }

    @GetMapping("/search")
    public ModelAndView viewCustomerSearch(@RequestParam(value = "search", required = false) String search) {

        ModelAndView mav = new ModelAndView("views/customer/listupCustomer");

        // 서치 없이 목록을 출력 할때
        if (search.isBlank() || search.isEmpty()) {
            int totalCustomerCount = customerService.getTotalCustomerCount();
            int rowsPerPage = 8;
            int pageNo = 1;

            PaginationUtils pagination = new PaginationUtils(totalCustomerCount, rowsPerPage, pageNo);
            int startRow = pagination.getStartRow();
            int endRow = pagination.getEndRow();

            List<CustomerNamecardVO> list = customerService.getAllCustomers(startRow, rowsPerPage);

            mav.addObject("cardList", list);
            mav.addObject("totalCustomerCount", totalCustomerCount);
            mav.addObject("currentPage", pageNo);
            mav.addObject("startRow", startRow);
            mav.addObject("endRow", endRow);
            mav.addObject("countPerPage", rowsPerPage);
            mav.addObject("hasNextPage", pagination.hasNextPage() ? "true" : "false");
            mav.addObject("hasPrevPage", pagination.hasPreviousPage() ? "true" : "false");

        } else {

        }

        return mav;
    }
}
