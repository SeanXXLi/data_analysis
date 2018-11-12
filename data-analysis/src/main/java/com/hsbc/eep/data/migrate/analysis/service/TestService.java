package com.hsbc.eep.data.migrate.analysis.service;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.hsbc.eep.data.migrate.analysis.domian.Employee;
 
 
@Service
public class TestService {
 
    @Autowired
    private JdbcTemplate jdbcTemplate;
 
    public List getList(){
    	
        String sql = "select employee_id,first_name,last_name,email,phone_number from employees";
        return (List) jdbcTemplate.query(sql, new RowMapper(){
 
            public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            	Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
                employee.setPhoneNumber(rs.getString("PHONE_NUMBER"));
                employee.setFirstName(rs.getString("FIRST_NAME"));
                employee.setLastName(rs.getString("LAST_NAME"));
                employee.setEmail(rs.getString("EMAIL"));
                return employee;
            }
 
        });
    }
}
