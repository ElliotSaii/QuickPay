package com.quickpay.commons.utils;
import com.quickpay.commons.constant.CacheKeys;
import com.quickpay.commons.constant.ConstantKeys;
import com.quickpay.commons.exceptions.AuthorizeException;
import com.quickpay.commons.exceptions.BusinessException;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.util.StringUtils;

import java.time.Duration;

public class CacheUtils {
    private static RedissonClient redissonClient;
    private static final long PWDFAIL_PER_MINUTES = 30L;
    private static final long PWDFAIL_LIMIT = 5;
    private static final long PHONE_LOGIN_OTP_PER_MINUTES = 1;
    private static final long APP_TOKEN_EXPIRE_MINUTES = 2 * 24 * 60L;
    private static final String TOKEN_REGEX = "^[0-9a-zA-Z]{32}$";



    public static void removePwdFailCount(String account) {
        String key = CacheKeys.APP_PWD_FAIL_COUNT + account;
        getRedissonClient().getLongAdder(key).delete();
    }

    public static boolean isReachPwdFailLimit(String account) {
        String key = CacheKeys.APP_PWD_FAIL_COUNT + account;
        //Do not use RLongAdder here. It is not saved to Redis in a single instance.
        //RLongAdder longAdder = redissonClient.getLongAdder(key);
        RAtomicLong atomicLong = getRedissonClient().getAtomicLong(key);
        long count = atomicLong.addAndGet(1);
        if (count == 1) {
            // the first time, set the expire time
            atomicLong.expire(Duration.ofMinutes(PWDFAIL_PER_MINUTES));
        }
        return count > PWDFAIL_LIMIT;
    }


    public static void expireAppToken(String token) {
        String key = CacheKeys.APP_TOKEN + token;
        // Expires after 10 seconds delay
        getRedissonClient().getBucket(key).expire(Duration.ofSeconds(30));
    }

    public static void deleteAppToken(String token) {
        String key = CacheKeys.APP_TOKEN + token;
        // Expires after 10 seconds delay
        getRedissonClient().getBucket(key).delete();
    }


    /**
     *Randomly generate a token with a length of 32 characters
     *
     * @return
     */
    public static String newToken() {
        return RandomUtils.randomBase64(24)
                .replace('-', 'x')
                .replace('_', '0');
    }

    public static boolean isValidToken(String tokenStr) {
        if (!StringUtils.hasText(tokenStr)) {
            return false;
        }
        return tokenStr.matches(TOKEN_REGEX);
    }


    public static void cacheOtp(String otp){
        String key = CacheKeys.PHONE_LOGIN_OTP + otp;
        getRedissonClient().getBucket(key).setIfExists(
                key, Duration.ofMinutes(PHONE_LOGIN_OTP_PER_MINUTES));

    }

    public static boolean isBlackIp(String ip) {
        return getRedissonClient().getBucket(CacheKeys.BLACK_IP + ip).isExists();
    }


    private static RedissonClient getRedissonClient(){
        if (redissonClient != null) return redissonClient;
        synchronized (CacheUtils.class){
            if (redissonClient != null) return redissonClient;
            redissonClient = SpringContextUtils.getBean(RedissonClient.class);
        }
        return  redissonClient;
    }

    public static boolean isValidPhoneOtp(String otp) {
        RBucket<Object> bucket = getRedissonClient().getBucket(CacheKeys.PHONE_LOGIN_OTP);
        if(!bucket.isExists()){
            return false;
        }
        String cachedOtp = (String) bucket.get();
        boolean isOtpCodeEquals = org.apache.commons.lang3.StringUtils.equals(otp, cachedOtp);
        if(!isOtpCodeEquals){
            return false;
        }
        return true;

    }
}
