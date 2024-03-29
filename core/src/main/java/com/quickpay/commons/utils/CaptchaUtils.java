package com.quickpay.commons.utils;
import com.quickpay.business.dto.CaptchaResponseDto;
import com.quickpay.commons.constant.CacheKeys;
import com.quickpay.commons.exceptions.BusinessException;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Slf4j
public class CaptchaUtils {
    private static final RedissonClient redissonClient = SpringContextUtils.getBean(RedissonClient.class);

    /**
     * Don't let anyone initantiate this class
     */
    private CaptchaUtils() {

    }


    public static CaptchaResponseDto generateCaptcha() {
        try {
            SpecCaptcha captcha = new SpecCaptcha(130, 48, 5);
            captcha.setCharType(Captcha.TYPE_NUM_AND_UPPER);
            captcha.setFont(Captcha.FONT_2);

            final String codeRef = UUID.randomUUID().toString().replace("-", "");
            String code = captcha.text();

            String redisKey = CacheKeys.CAPTCHA + codeRef;

            redissonClient.getBucket(redisKey).set(code, Duration.ofMinutes(5));


            log.debug(">> captcha code: {}", code);

            return CaptchaResponseDto.builder()
                    .codeRef(codeRef)
                    .base64img(captcha.toBase64(""))
                    .build();
        } catch (IOException | FontFormatException e) {
            log.error("Failed to generate image verification code", e);
            throw new BusinessException("Failed to generate verification code image");
        }
    }



    /**
     * Verify whether the verification code submitted by the user is correct
     *
     * @param codeRef code reference
     * @param code    verification code
     */
    public static void validateCaptcha(String codeRef, String code) {

        Assert.hasText(codeRef,"Please enter correct verify code");
        Assert.isTrue(StringUtils.hasText(code) && code.length() == 5, "Please enter correct verify code");
        String redisKey = CacheKeys.CAPTCHA + codeRef;
        RBucket<String> bucket = redissonClient.getBucket(redisKey);
        try {
            final String string = bucket.get();
            Assert.hasText(string,"The verification code has expired。");
            Assert.isTrue(code.equalsIgnoreCase(string), "Please enter correct verify code");
        } finally {
            bucket.delete();
        }

    }



}

