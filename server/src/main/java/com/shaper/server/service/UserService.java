package com.shaper.server.service;

import com.shaper.server.model.dto.LoginRequestDTO;
import com.shaper.server.model.dto.RegisterRequestDTO;
import com.shaper.server.model.dto.UserDTO;
import com.shaper.server.model.dto.UserTokenDTO;
import com.shaper.server.model.entity.Company;
import com.shaper.server.model.entity.CompanyDepartment;
import com.shaper.server.model.entity.Hire;
import com.shaper.server.model.entity.HrUser;

public interface UserService {

    /**
     * Register a new HR user with a new company
     * @param registerRequestDTO Registration details
     * @return UserDTO with the registered user details
     */
    UserDTO registerHr(RegisterRequestDTO registerRequestDTO);
    
    /**
     * Register a new Hire user
     * @param registerRequestDTO Registration details
     * @param hrUser The HR user registering the hire
     * @param department The department to assign the hire to
     * @return UserDTO with the registered user details
     */
    UserDTO registerHire(RegisterRequestDTO registerRequestDTO, HrUser hrUser, CompanyDepartment department);
    
    /**
     * Create a new company
     * @param companyName The name of the company
     * @return The created company
     */
    Company createCompany(String companyName);
    
    /**
     * Create a new department
     * @param departmentName The name of the department
     * @param company The company the department belongs to
     * @param hrUser The HR user creating the department
     * @return The created department
     */
    CompanyDepartment createDepartment(String departmentName, Company company, HrUser hrUser);
    
    /**
     * Authenticate a user and generate a JWT token
     * @param loginRequestDTO Login credentials
     * @return UserTokenDTO with the authenticated user details and JWT token
     */
    UserTokenDTO login(LoginRequestDTO loginRequestDTO);
    
    /**
     * Find an HR user by email
     * @param email The email to search for
     * @return The found HR user or null
     */
    HrUser findHrByEmail(String email);
    
    /**
     * Find a Hire user by email
     * @param email The email to search for
     * @return The found Hire user or null
     */
    Hire findHireByEmail(String email);
    
    /**
     * Find a company by ID
     * @param companyId The company ID
     * @return The found company or null
     */
    Company findCompanyById(String companyId);
    
    /**
     * Find a department by ID
     * @param departmentId The department ID
     * @return The found department or null
     */
    CompanyDepartment findDepartmentById(String departmentId);
}
