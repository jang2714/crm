package kr.code.main.customer.controller;

import kr.code.main.common.File.service.FileService;
import kr.code.main.common.department.domain.DepartmentVO;
import kr.code.main.common.department.service.DepartmentService;
import kr.code.main.common.position.domain.PositionVO;
import kr.code.main.common.position.service.PositionService;
import kr.code.main.customer.domain.CustomerNamecardVO;
import kr.code.main.customer.domain.CustomerVO;
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
    private final PositionService positionService;
    private final DepartmentService departmentService;
    private final FileService fileService;

    @GetMapping("/create")
    public ModelAndView viewCreateCustomer() {

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

        CustomerVO customer = customerService.findByUid(customerUid);
        mav.addObject("customer", customer);

        List<PositionVO> positions = positionService.GetAllPosition();
        mav.addObject("positions", positions);

        List<DepartmentVO> departments = departmentService.GetAllDepartment();
        mav.addObject("departments", departments);

        // file 처리 필요
        // ajax로 얻어 오는 방식으로 처리 완료.

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
    public ModelAndView viewCustomerSearch(@RequestParam(value = "search") String search) {

        ModelAndView mav = new ModelAndView("views/customer/listupCustomer");

        // 서치 없이 목록을 출력 할때
        if (!search.isEmpty()) {

            List<CustomerNamecardVO> list = customerService.getCustomersByTag(search);
            int totalCustomerCount = list.size();
            int rowsPerPage = 8;
            int pageNo = 1;

            PaginationUtils pagination = new PaginationUtils(totalCustomerCount, rowsPerPage, pageNo);
            int startRow = pagination.getStartRow();
            int endRow = pagination.getEndRow();

            attachObjectsToModel(pageNo, mav, totalCustomerCount, rowsPerPage, pagination, startRow, endRow, list);

        } else {

            int totalCustomerCount = customerService.getTotalCustomerCount();
            int rowsPerPage = 8;

            PaginationUtils pagination = new PaginationUtils(totalCustomerCount, rowsPerPage, 1);
            int startRow = pagination.getStartRow();
            int endRow = pagination.getEndRow();

            List<CustomerNamecardVO> list = customerService.getAllCustomers(startRow, rowsPerPage);

            attachObjectsToModel(1, mav, totalCustomerCount, rowsPerPage, pagination, startRow, endRow, list);
        }

        return mav;
    }
}
