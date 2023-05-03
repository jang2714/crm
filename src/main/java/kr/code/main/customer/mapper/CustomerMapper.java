package kr.code.main.customer.mapper;

import kr.code.main.common.department.domain.DepartmentVO;
import kr.code.main.common.position.domain.PositionVO;
import kr.code.main.customer.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface CustomerMapper {

    int getTotalCustomerCount(Map<String, Object> params);

    @Select("SELECT * FROM tbl_customer WHERE cust_name = #{name}")
    @Results(id="customerResultMap", value={
            @Result(property="customerUid", column="cust_uid"),
            @Result(property="customerName", column="cust_name"),
            @Result(property="customerGender", column="cust_gender"),
            @Result(property="customerBirth", column="cust_birth"),
            @Result(property="address", column="cust_addr"),
            @Result(property="postcode", column="addr_post"),
            @Result(property="customerEmail", column="cust_email"),
            @Result(property="phoneNumber", column="phone_number"),
            @Result(property="companyName", column="company_name"),
            @Result(property="posCode", column="pos_code"),
            @Result(property="deptCode", column="dept_code"),
            @Result(property="attachedCnt", column="attached_cnt"),
            @Result(property="createDate", column="create_date"),
    })
    Optional<CustomerVO> findByName(String name);

    @Select("SELECT * FROM tbl_customer WHERE cust_uid = #{customerUid}")
    @Results(id="customerResultMap2", value={
            @Result(property="customerUid", column="cust_uid"),
            @Result(property="customerName", column="cust_name"),
            @Result(property="customerGender", column="cust_gender"),
            @Result(property="customerBirth", column="cust_birth"),
            @Result(property="address", column="cust_addr"),
            @Result(property="postcode", column="addr_post"),
            @Result(property="customerEmail", column="cust_email"),
            @Result(property="phoneNumber", column="phone_number"),
            @Result(property="companyName", column="company_name"),
            @Result(property="posCode", column="pos_code"),
            @Result(property="deptCode", column="dept_code"),
            @Result(property="attachedCnt", column="attached_cnt"),
            @Result(property="createDate", column="create_date"),
    })
    Optional<CustomerVO> findByUid(String customerUid);

    List<CustomerNamecardVO> getAllCustomer(Map<String, Object> params);

    List<CustomerNamecardVO> getCustomersByTag(Map<String, Object> params);

    int createCustomer(CustomerVO customer);

    List<CustomerTagVO> getCustomerTagsById(String customerId);

    int insertCustomerAndTag(Map<String, Object> params);

    @Select("SELECT * FROM tbl_position")
    List<PositionVO> getPositionMap();

    @Select("SELECT * FROM tbl_department")
    List<DepartmentVO> getDepartmentList();
}
