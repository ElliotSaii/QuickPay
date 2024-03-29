package com.quickpay.business.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CaptchaResponseDto {

    private String codeRef;


    private String base64img;
}
