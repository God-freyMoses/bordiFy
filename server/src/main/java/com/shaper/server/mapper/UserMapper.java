package com.shaper.server.mapper;

import com.shaper.server.model.dto.RegisterRequestDTO;
import com.shaper.server.model.dto.UserDTO;
import com.shaper.server.model.dto.UserTokenDTO;
import com.shaper.server.model.entity.Company;
import com.shaper.server.model.entity.CompanyDepartment;
import com.shaper.server.model.entity.Hire;
import com.shaper.server.model.entity.HrUser;
import com.shaper.server.model.entity.User;
import com.shaper.server.model.enums.UserRole;

import java.util.HashSet;

public class UserMapper {

    // RegisterRequestDTO to HrUser Entity
    public static HrUser registerRequestToHrEntity(RegisterRequestDTO dto, Company company) {
        HrUser hrUser = new HrUser();
        hrUser.setEmail(dto.getEmail());
        hrUser.setPassword(dto.getPassword());
        hrUser.setFirstName(dto.getFirstName());
        hrUser.setLastName(dto.getLastName());
        hrUser.setRole(UserRole.HR_MANAGER);
        hrUser.setCompany(company);
        hrUser.setDepartments(new HashSet<>());
        hrUser.setHires(new HashSet<>());
        hrUser.setTemplates(new HashSet<>());
        return hrUser;
    }

    // RegisterRequestDTO to Hire Entity
    public static Hire registerRequestToHireEntity(RegisterRequestDTO dto, HrUser hrUser, CompanyDepartment department) {
        Hire hire = new Hire();
        hire.setEmail(dto.getEmail());
        hire.setPassword(dto.getPassword());
        hire.setFirstName(dto.getFirstName());
        hire.setLastName(dto.getLastName());
        hire.setRole(UserRole.NEW_HIRE);
        hire.setGender(dto.getGender());
        hire.setTitle(dto.getTitle());
        hire.setPictureUrl(dto.getPictureUrl());
        hire.setRegisteredByHr(hrUser);
        hire.setDepartment(department);
        hire.setProgressItems(new HashSet<>());
        return hire;
    }

    // User entity to UserDTO
    public static UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId().toString());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setRole(user.getRole().name());
        userDTO.setCreatedAt(user.getCreatedAt().toString());
        
        // Set user type based on the concrete class
        if (user instanceof HrUser) {
            userDTO.setUserType("HR");
        } else if (user instanceof Hire) {
            userDTO.setUserType("HIRE");
        }
        
        return userDTO;
    }

    // User entity to UserTokenDTO
    public static UserTokenDTO userToUserTokenDTO(User user, String token) {
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        userTokenDTO.setUser(userToUserDTO(user));
        userTokenDTO.setToken(token);
        return userTokenDTO;
    }
}
