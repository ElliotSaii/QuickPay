package com.quickpay.business.vo;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;


@Getter
@Setter
public class RegisterVo {
    @NotEmpty(message = "[FirstName] can't be empty")
    @NotBlank(message = "[FirstName] can't be blank")
    @NotNull(message = "[FirstName] can't be null")
    private String firstName;

    @NotEmpty(message = "[LastName] can't be empty")
    @NotBlank(message = "[LastName] can't be blank")
    @NotNull(message = "[LastName] can't be null")
    private String lastName;



    @NotEmpty(message = "[Phone] can't be empty")
    @NotBlank(message = "[Phone] can't be blank")
    @NotNull(message = "[Phone] can't be null")

    @Pattern(regexp="^09\\d{9}$", message="Invalid phone number")
    private String phone;







}
