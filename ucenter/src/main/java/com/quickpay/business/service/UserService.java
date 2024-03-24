package com.quickpay.business.service;

import com.quickpay.business.dto.UserDto;
import com.quickpay.business.entity.User;
import com.quickpay.business.vo.LoginVo;
import com.quickpay.business.vo.RegisterVo;

public interface UserService  {


    User getByUserId(Long id);

    void doRegister(RegisterVo registerVo);

    User getUserByPhone(String phone);

    void doLogin(String otp);

    String getOtpByPhone(String phone);
}
