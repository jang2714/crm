package kr.code.main.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/users")
public class LoginController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ApiResponse signUp(@RequestBody SignUpDto signUpDto){
		return userService.signUp(signUpDto);
	}

	@PostMapping("/login")
	public ApiResponse login(@RequestBody LoginDto loginDto){
		return userService.login(loginDto);
	}
}