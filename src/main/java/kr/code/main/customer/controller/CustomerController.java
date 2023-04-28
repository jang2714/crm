package kr.code.main.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping("/create")
    public ModelAndView viewCreateCustomer() {
        return new ModelAndView("views/customer/createCustomer");
    }
}
