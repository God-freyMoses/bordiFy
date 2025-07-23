package com.shaper.server.controller;

import com.shaper.server.model.dto.LoginRequestDTO;
import com.shaper.server.model.dto.RegisterRequestDTO;
import com.shaper.server.model.dto.UserDTO;
import com.shaper.server.model.dto.UserTokenDTO;
import com.shaper.server.service.UserService;
import com.shaper.server.system.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // REGISTER USER

    @PostMapping("/register")
    public ResponseEntity<Result> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        UserDTO userDTO = userService.register(registerRequestDTO);
        Result result = new Result(200, true, "User registered successfully!", userDTO);
        return ResponseEntity.ok(result);
    }


    // LOGIN USER

    @PostMapping("/login")
    public ResponseEntity<Result> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        UserTokenDTO userTokenDTO = userService.login(loginRequestDTO);
        Result result = new Result(200, true, "User logged in successfully!", userTokenDTO);
        return ResponseEntity.ok(result);
    }


}
