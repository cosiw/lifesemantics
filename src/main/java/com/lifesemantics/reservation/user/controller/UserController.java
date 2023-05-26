package com.lifesemantics.reservation.user.controller;

import com.lifesemantics.reservation.common.Exception.CustomException;
import com.lifesemantics.reservation.user.dto.LoginRequestDTO;
import com.lifesemantics.reservation.user.dto.UserRequestDTO;
import com.lifesemantics.reservation.user.dto.UserResponseDTO;
import com.lifesemantics.reservation.user.service.UserService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/api/login")
    public ResponseEntity login(@Validated @RequestBody LoginRequestDTO req, BindingResult result){
        if(result.hasErrors()){
            throw new CustomException(result.getFieldError().getDefaultMessage(), HttpServletResponse.SC_BAD_REQUEST);
        }
        UserResponseDTO res = userService.login(req);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/api/join")
    public ResponseEntity join(@Validated @RequestBody UserRequestDTO req, BindingResult result){

        if(result.hasErrors()){
            throw new CustomException(result.getFieldError().getDefaultMessage(), HttpServletResponse.SC_BAD_REQUEST);
        }
        int res = userService.join(req);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
