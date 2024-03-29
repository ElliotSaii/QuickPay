package com.quickpay.business.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@EqualsAndHashCode()
public class LoginVo {
    @NotNull(message = "[OTP] code is require")
    @NotEmpty(message = "[OTP] code is require")
    @NotBlank(message = "[OTP] code is required")
    @Length(max = 6, min = 6, message = "[OTP] must contains 6 digit numbers")
    private String otp;

    @NotEmpty(message = "[Phone] number is require")
    @NotBlank(message = "[Phone] number is require")
    @NotNull(message = "[Phone] number is require")
    @Pattern(regexp="^\\+95\\s\\d{11}$", message="Invalid phone number")
    private String phone;
}
