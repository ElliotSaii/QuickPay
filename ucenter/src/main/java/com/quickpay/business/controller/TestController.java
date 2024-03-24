package com.quickpay.business.controller;

import com.quickpay.business.dto.ResponeseDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping(value = "/api/test")
public class TestController {

    @PostMapping(value = "/signup")
    public ResponeseDto signup(@Valid @RequestBody SignUpVo signUpVo){

        return ResponeseDto.success(signUpVo.getUsername() + signUpVo.getEmail());
    }
}
@Data
class SignUpVo {
    @NotBlank(message = "user name is required")
    @NotEmpty(message = "user is empty")
    @NotNull(message = "user is null")
    private String username;

    @NotBlank(message = "email is necessary")
    @NotEmpty(message = "email is empty")
    @NotNull(message = "email is null")

    private String email;
}
