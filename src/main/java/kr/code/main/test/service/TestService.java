package kr.code.main.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.code.main.test.mapper.TestMapper;
import kr.code.main.test.vo.TestVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {

	private final TestMapper mapper;
	
	public List<TestVO> getList() throws Exception {
		return mapper.getTest();
	}
	
}
