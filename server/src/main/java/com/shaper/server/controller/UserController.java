package com.shaper.server.controller;

import com.shaper.server.model.dto.LoginRequestDTO;
import com.shaper.server.model.dto.RegisterRequestDTO;
import com.shaper.server.model.dto.UserDTO;
import com.shaper.server.model.dto.UserTokenDTO;
import com.shaper.server.model.entity.CompanyDepartment;
import com.shaper.server.model.entity.HrUser;
import com.shaper.server.service.UserService;
import com.shaper.server.system.Result;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for user registration and authentication")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // REGISTER HR USER (PUBLIC)
    @Operation(
        summary = "Register HR Manager",
        description = "Register a new HR Manager with a company. This is a public endpoint."
    )
    @PostMapping("/register/hr")
    public ResponseEntity<Result> registerHr(@RequestBody RegisterRequestDTO registerRequestDTO) {
        UserDTO userDTO = userService.registerHr(registerRequestDTO);
        Result result = new Result(200, true, "HR Manager registered successfully!", userDTO);
        return ResponseEntity.ok(result);
    }

    // REGISTER HIRE USER (HR ONLY)
    @Operation(
        summary = "Register New Hire",
        description = "Register a new hire. Only accessible by HR Managers."
    )
    @PostMapping("/register/hire")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Result> registerHire(@RequestBody RegisterRequestDTO registerRequestDTO) {
        // Get the authenticated HR user
        HrUser hrUser = userService.findHrByEmail(registerRequestDTO.getEmail());
        
        // Get the department
        CompanyDepartment department = null;
        if (registerRequestDTO.getDepartmentId() != null) {
            department = userService.findDepartmentById(registerRequestDTO.getDepartmentId());
        }
        
        UserDTO userDTO = userService.registerHire(registerRequestDTO, hrUser, department);
        Result result = new Result(200, true, "Hire registered successfully!", userDTO);
        return ResponseEntity.ok(result);
    }

    // LOGIN USER
    @Operation(
        summary = "User Login",
        description = "Authenticate a user and generate a JWT token"
    )
    @PostMapping("/login")
    public ResponseEntity<Result> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        UserTokenDTO userTokenDTO = userService.login(loginRequestDTO);
        Result result = new Result(200, true, "User logged in successfully!", userTokenDTO);
        return ResponseEntity.ok(result);
    }
}
