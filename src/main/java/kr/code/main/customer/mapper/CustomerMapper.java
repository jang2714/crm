package kr.code.main.customer.mapper;

import kr.code.main.customer.domain.CustomerNamecardVO;
import kr.code.main.customer.domain.CustomerVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CustomerMapper {

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
            @Result(property="deptCode", column="dept_code")
    })
    Optional<CustomerVO> findByName(String name);

    List<CustomerNamecardVO> getAllCustomer();
}
