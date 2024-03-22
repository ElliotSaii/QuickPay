package com.quickpay.business.controller;

import com.quickpay.business.service.UserService;
import com.quickpay.business.vo.ResponeseDto;
import com.quickpay.business.vo.ResultVo;
import com.quickpay.business.vo.UserVo;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/index")
    public ResponeseDto<String> index(){
        return ResponeseDto.success("Welcome ..");
    }
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponeseDto<?> register(@Valid @RequestBody UserVo userVo){
        Assert.notNull(userVo, "User details can't be null");
        return ResponeseDto.success();
    }
}
