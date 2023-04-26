package kr.code.main.test.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.code.main.test.service.TestService;
import kr.code.main.test.vo.TestVO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TestController {
	
	private final TestService service;

	@GetMapping("/test")
	public ModelAndView test() {
		ModelAndView view = new ModelAndView();
		view.setViewName("views/home");
		return view;
	}
	
	@GetMapping("/test/data")
	@ResponseBody
	public List<TestVO> testData() throws Exception {
		return service.getList();
	}
}
