package com.costcommission.controller;

import com.costcommission.dto.LoginDTO;
import com.costcommission.dto.ResponseDTO;
import com.costcommission.dto.UserDTO;
import com.costcommission.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<Object> validateUser(@RequestBody LoginDTO loginDTO) {
        ResponseDTO responseDTO;
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        try {
            UserDTO savedUser = loginService.validateUser(loginDTO.getLoginId(), loginDTO.getPassword());
            if (savedUser != null)
                responseDTO = new ResponseDTO("Valid User", true, savedUser);
            else
                responseDTO = new ResponseDTO("Invalid user", false, null);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

}
