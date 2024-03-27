//package com.quickpay.commons.config;
//
//
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.redisson.config.SingleServerConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.StringUtils;
//
//import java.io.IOException;
//
//
//@Slf4j
//@Configuration
//public class RedissonConfig {
//
//    @Value("${spring.data.redis.myredisson}")
//    private String myredisson;
//
//    @Autowired
//    private RedisProperties redisProperties;
//
//    @Bean
//    public RedissonClient redissonClient() throws IOException {
//        final String host = redisProperties.getHost();
//        final int port = redisProperties.getPort();
//        final String password = redisProperties.getPassword();
//        final int database = redisProperties.getDatabase();
//        //final Duration timeout = redisProperties.getTimeout();
//        Config config = Config.fromYAML(myredisson);
//
//        SingleServerConfig singleServerConfig = config.useSingleServer();
//        //可以用"rediss://"来启用SSL连接
//        String url = "redis://" + host + ":" + port;
//        log.info("RedissonClient:{}",url);
//        singleServerConfig.setAddress(url);
//        if (StringUtils.hasText(password)){
//            singleServerConfig.setPassword(password);
//        }
//
//        singleServerConfig.setDatabase(database);
//
//
//        return Redisson.create(config);
//    }
//
//
//
//
//}
