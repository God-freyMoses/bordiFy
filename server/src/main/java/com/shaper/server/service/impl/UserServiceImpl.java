package com.shaper.server.service.impl;

import com.shaper.server.exception.DataNotFoundException;
import com.shaper.server.exception.UnAutororizedException;
import com.shaper.server.mapper.UserMapper;
import com.shaper.server.model.dto.LoginRequestDTO;
import com.shaper.server.model.dto.RegisterRequestDTO;
import com.shaper.server.model.dto.UserDTO;
import com.shaper.server.model.dto.UserTokenDTO;
import com.shaper.server.model.entity.Company;
import com.shaper.server.model.entity.CompanyDepartment;
import com.shaper.server.model.entity.CompanySub;
import com.shaper.server.model.entity.Hire;
import com.shaper.server.model.entity.HrUser;
import com.shaper.server.model.enums.UserRole;
import com.shaper.server.repository.CompanyDepartmentRepository;
import com.shaper.server.repository.CompanyRepository;
import com.shaper.server.repository.CompanySubRepository;
import com.shaper.server.repository.HireRepository;
import com.shaper.server.repository.HrUserRepository;
import com.shaper.server.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final HrUserRepository hrUserRepository;
    private final HireRepository hireRepository;
    private final CompanyRepository companyRepository;
    private final CompanyDepartmentRepository departmentRepository;
    private final CompanySubRepository companySubRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserServiceImpl(
            HrUserRepository hrUserRepository,
            HireRepository hireRepository,
            CompanyRepository companyRepository,
            CompanyDepartmentRepository departmentRepository,
            CompanySubRepository companySubRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {
        this.hrUserRepository = hrUserRepository;
        this.hireRepository = hireRepository;
        this.companyRepository = companyRepository;
        this.departmentRepository = departmentRepository;
        this.companySubRepository = companySubRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public UserDTO registerHr(RegisterRequestDTO dto) {
        // Check if email already exists
        if (hrUserRepository.findByEmail(dto.getEmail()).isPresent() ||
            hireRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UnAutororizedException("Email already exists!");
        }
        
        // Create company if company name is provided
        Company company;
        if (dto.getCompanyId() != null && !dto.getCompanyId().isEmpty()) {
            company = findCompanyById(dto.getCompanyId());
        } else if (dto.getCompanyName() != null && !dto.getCompanyName().isEmpty()) {
            company = createCompany(dto.getCompanyName());
        } else {
            throw new UnAutororizedException("Company information is required!");
        }
        
        // Create HR user
        HrUser hrUser = UserMapper.registerRequestToHrEntity(dto, company);
        hrUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        HrUser savedHrUser = hrUserRepository.save(hrUser);
        
        return UserMapper.userToUserDTO(savedHrUser);
    }

    @Override
    @Transactional
    public UserDTO registerHire(RegisterRequestDTO dto, HrUser hrUser, CompanyDepartment department) {
        // Check if email already exists
        if (hrUserRepository.findByEmail(dto.getEmail()).isPresent() ||
            hireRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UnAutororizedException("Email already exists!");
        }
        
        // Create Hire user
        Hire hire = UserMapper.registerRequestToHireEntity(dto, hrUser, department);
        hire.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        Hire savedHire = hireRepository.save(hire);
        
        return UserMapper.userToUserDTO(savedHire);
    }

    @Override
    @Transactional
    public Company createCompany(String companyName) {
        // Check if company name already exists
        if (companyRepository.findByName(companyName).isPresent()) {
            throw new UnAutororizedException("Company name already exists!");
        }
        
        // Create company
        Company company = new Company();
        company.setName(companyName);
        company.setHrUsers(new HashSet<>());
        company.setDepartments(new HashSet<>());
        
        Company savedCompany = companyRepository.save(company);
        
        // Create default subscription (free tier)
        CompanySub subscription = new CompanySub();
        subscription.setCompany(savedCompany);
        subscription.setPlan(CompanySub.SubscriptionPlan.BASIC);
        
        companySubRepository.save(subscription);
        
        return savedCompany;
    }

    @Override
    @Transactional
    public CompanyDepartment createDepartment(String departmentName, Company company, HrUser hrUser) {
        // Create department
        CompanyDepartment department = new CompanyDepartment();
        department.setName(departmentName);
        department.setCompany(company);
        department.setCreatedByHr(hrUser);
        department.setHires(new HashSet<>());
        department.setAssignedTemplates(new HashSet<>());
        
        return departmentRepository.save(department);
    }

    @Override
    public UserTokenDTO login(LoginRequestDTO loginRequestDTO) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
            );
            
            if (authenticate.isAuthenticated()) {
                // Find user by email
                HrUser hrUser = hrUserRepository.findByEmail(loginRequestDTO.getEmail()).orElse(null);
                if (hrUser != null) {
                    String token = jwtService.generateToken(hrUser);
                    return UserMapper.userToUserTokenDTO(hrUser, token);
                }
                
                Hire hire = hireRepository.findByEmail(loginRequestDTO.getEmail()).orElse(null);
                if (hire != null) {
                    String token = jwtService.generateToken(hire);
                    return UserMapper.userToUserTokenDTO(hire, token);
                }
            }
            
            throw new UnAutororizedException("Invalid credentials!");
        } catch (Exception e) {
            throw new UnAutororizedException("Invalid credentials!");
        }
    }

    @Override
    public HrUser findHrByEmail(String email) {
        return hrUserRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("HR user not found with email: " + email));
    }

    @Override
    public Hire findHireByEmail(String email) {
        return hireRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("Hire user not found with email: " + email));
    }

    @Override
    public Company findCompanyById(String companyId) {
        try {
            Integer id = Integer.parseInt(companyId);
            return companyRepository.findById(id)
                    .orElseThrow(() -> new DataNotFoundException("Company not found with id: " + companyId));
        } catch (NumberFormatException e) {
            throw new UnAutororizedException("Invalid company ID format!");
        }
    }

    @Override
    public CompanyDepartment findDepartmentById(String departmentId) {
        try {
            Integer id = Integer.parseInt(departmentId);
            return departmentRepository.findById(id)
                    .orElseThrow(() -> new DataNotFoundException("Department not found with id: " + departmentId));
        } catch (NumberFormatException e) {
            throw new UnAutororizedException("Invalid department ID format!");
        }
    }
}
