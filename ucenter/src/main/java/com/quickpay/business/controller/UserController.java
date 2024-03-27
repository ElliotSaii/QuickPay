package com.quickpay.business.controller;

import com.quickpay.business.entity.User;
import com.quickpay.business.service.UserService;
import com.quickpay.business.vo.LoginVo;
import com.quickpay.business.vo.RegisterVo;
import com.quickpay.business.dto.ResponeseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponeseDto<String> register(@Valid @RequestBody RegisterVo registerVo){
        ResponeseDto<String> responeseDto =new ResponeseDto<>();

       userService.doRegister(registerVo);
           responeseDto.setMsg("Register Successfully");
           responeseDto.setSuccess(true);
           responeseDto.setCode(HttpStatus.CREATED.value());
        return responeseDto;
    }
    @PostMapping(value = "/login")
    public ResponeseDto<Object> login(@RequestParam("otp") String otp){
        ResponeseDto<Object> responeseDto = new ResponeseDto<>();
        userService.doLogin(otp);

        responeseDto.setMsg("Login Successfully");
        responeseDto.setSuccess(true);
        responeseDto.setCode(HttpStatus.OK.value());
        return responeseDto;
    }

    @PostMapping(value = "/req/phone/otp")
    public ResponeseDto<Map<String, String>> getOtpByPhone(@RequestParam("phone") String phone){
     String otp =   userService.getOtpByPhone(phone);
    Map<String, String> map =new HashMap<>();
    map.put("otp", otp);
     return  ResponeseDto.success(map);
    }
}
