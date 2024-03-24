package com.quickpay.business.vo;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class RegisterVo {
    @NotEmpty(message = "[FirstName] can't be blank")
    private String firstName;
//    @NotEmpty(message = "[LastName] can't be blank")
    private String lastName;

//    @NotBlank(message = "[Email] can't be blank")
//    @Email(message = "Email is not valid")
    private String email;
//    @NotBlank(message = "[Address] can't be blank")
    private String address;
//    @NotBlank(message =  "[contactDetails] can't be blank")
    private String contactDetails;

}
