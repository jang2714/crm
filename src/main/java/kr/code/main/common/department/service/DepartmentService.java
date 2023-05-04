package kr.code.main.common.department.service;

import kr.code.main.common.department.domain.Department;
import kr.code.main.common.department.domain.DepartmentVO;
import kr.code.main.common.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository repository;

    public List<DepartmentVO> GetAllDepartment() {
        List<Department> departmentList = repository.findAll();

        return departmentList.stream().map( department -> {
            return new DepartmentVO(department.getDeptCode(), department.getDeptName());
        }).toList();
    }
}
