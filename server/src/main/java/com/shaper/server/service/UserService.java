package com.shaper.server.service;

import com.shaper.server.model.dto.LoginRequestDTO;
import com.shaper.server.model.dto.RegisterRequestDTO;
import com.shaper.server.model.dto.UserDTO;
import com.shaper.server.model.dto.UserTokenDTO;
import com.shaper.server.model.entity.HrUser;
import com.shaper.server.model.entity.Hire;
import com.shaper.server.repository.HrUserRepository;
import com.shaper.server.repository.HireRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final HrUserRepository hrUserRepository;
    private final HireRepository hireRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(HrUserRepository hrUserRepository, HireRepository hireRepository, PasswordEncoder passwordEncoder) {
        this.hrUserRepository = hrUserRepository;
        this.hireRepository = hireRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerHr(RegisterRequestDTO dto) {
        HrUser hrUser = new HrUser();
        hrUser.setUsername(dto.getUsername());
        hrUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        hrUser.setRole("HR");
        hrUserRepository.save(hrUser);
    }

    public void createHire(String email) {
        Hire hire = new Hire();
        hire.setEmail(email);
        hire.setPassword(passwordEncoder.encode("defaultPassword"));
        hire.setRole("HIRE");
        hireRepository.save(hire);
    }

    // REGISTER

    UserDTO register(RegisterRequestDTO registerRequestDTO);

    // LOGIN

    UserTokenDTO login(LoginRequestDTO loginRequestDTO);

}
