package kr.code.main.user.controller;

import kr.code.main.user.dto.UserDto;
import kr.code.main.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //유저(직원)가입
    @GetMapping("/user/join")
    public ModelAndView dispJoin() {
        return new ModelAndView("/views/user/join");
    }

    //유저가입 처리
    @PostMapping("/user/join")
    @ResponseBody
    public String execJoin(UserDto userDto) {
        userService.joinUser(userDto);
        return "redirect:/users/loginForm";
    }

    // 로그인 페이지
    @GetMapping("/user/loginForm")
    public ModelAndView dispLogin() {
        return new ModelAndView("/views/user/loginForm");
    }

    // 로그인 결과
    @GetMapping("/user/login/result")
    public ModelAndView dispLoginResult() {
        return new ModelAndView("/index");
    }

    // 로그아웃 결과
//    @GetMapping("/user/login/result")
//    public ModelAndView dispLogout() {
//        return new ModelAndView("/index");
//    }

    // 내 정보 페이지
    @GetMapping("/user/info")
    public ModelAndView dispMyinfo() {
        return new ModelAndView("/views/user/myinfo");
    }

    // 권한 페이지
    @GetMapping("/user/admin")
    public ModelAndView dispAdmin() {
        return new ModelAndView("/views/user/admin");
    }

}
