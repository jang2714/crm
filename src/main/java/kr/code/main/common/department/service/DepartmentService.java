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

    private List<DepartmentVO> cachedList = null;

    public List<DepartmentVO> GetAllDepartment() {
        if (cachedList != null) {
            if (cachedList.size() == repository.count()) {
                return cachedList;
            }
        }

        List<Department> departmentList = repository.findAll();
        List<DepartmentVO> result = departmentList.stream().map( department -> {
            return new DepartmentVO(department.getDeptCode(), department.getDeptName());
        }).toList();
        if (result.size() > 0 && cachedList == null) {
            cachedList = result;
        }

        return result;
    }
}
