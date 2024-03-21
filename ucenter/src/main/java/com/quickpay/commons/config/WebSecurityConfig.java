package com.quickpay.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig  {

    public static final String[]openApi = {"/api/user/index"};

    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {
        http
                //csrf config
                .csrf((csrf)-> csrf.ignoringRequestMatchers("no-csrf"))
                //open api
                .authorizeHttpRequests((authz)-> authz
                        .requestMatchers(openApi).permitAll()
                        .anyRequest().authenticated())

        ;

        return http.build();

    }


//   @Bean
//    public WebSecurityCustomizer webSecurityCustomizer(){
//        //ignore folders to allow static resources to be released
//        return (web -> web.ignoring().requestMatchers("**/static/**", "static/js/**"));
//   }

}
