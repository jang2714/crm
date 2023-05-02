package kr.code.main.home;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public ModelAndView entryPoint() {
        return new ModelAndView("views/index");
    }

    @GetMapping("/index")
    public ModelAndView entryPoint2() {
        return new ModelAndView("views/index");
    }
}
