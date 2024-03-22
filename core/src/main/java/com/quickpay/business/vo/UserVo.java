package com.quickpay.business.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVo {
    private String name;
    private String email;
    private String address;
    private String contactDetails;
}
