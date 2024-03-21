package com.quickpay.business.controller;

import com.quickpay.business.vo.ResultVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @GetMapping(value = "/index")
    public ResultVo<String> index(){
        return ResultVo.success("Welcome ..");
    }
}
