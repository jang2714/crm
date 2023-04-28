package kr.code.main.user.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.login.mapper.LoginMapper;
import com.example.demo.login.vo.LoginVO;

import lombok.RequiredArgsConstructor;

@Service   // 스프링이 가진 annotation
@RequiredArgsConstructor   //lombok
public class LoginService {
	
	//@Autowired
	private final LoginMapper mapper;
	
	//로그인처리 메서
	public LoginVO getLogin(Map<String, Object> paramMap) throws Exception {
		return mapper.getLogin(paramMap);
	}

}
