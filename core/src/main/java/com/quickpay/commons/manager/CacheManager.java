package com.quickpay.commons.manager;


import com.quickpay.commons.constant.CacheKeys;
import com.quickpay.commons.exceptions.AuthorizeException;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CacheManager {

    private static final Long PHONE_LOGIN_OTP_EXPIRES_TIME = 1L;


   private  final RedissonClient redissonClient;

    public CacheManager(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public  void cacheOtp(String otp){
        String key = CacheKeys.PHONE_LOGIN_OTP;

        redissonClient.getBucket(key).set(
                otp, Duration.ofMinutes(PHONE_LOGIN_OTP_EXPIRES_TIME));

    }
    public  boolean isValidPhoneOtp(String otp) {
        RBucket<Object> bucket = redissonClient.getBucket(CacheKeys.PHONE_LOGIN_OTP);
        String cachedOtp = bucket.isExists() ? (String) bucket.get() : null;
        if (cachedOtp == null) {
            throw new AuthorizeException("Otp code is expired");
        }
        return  StringUtils.equals(otp, cachedOtp);

    }

}
