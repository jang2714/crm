package kr.code.main.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.code.main.test.vo.TestVO;

@Mapper
public interface TestMapper {
	
	public List<TestVO> getTest() throws Exception;
}
