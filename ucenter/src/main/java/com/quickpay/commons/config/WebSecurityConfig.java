package com.quickpay.commons.config;

import com.quickpay.commons.exceptions.CustomAccessDeniedHandler;
import com.quickpay.commons.exceptions.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    public static final String[]openApi = {"/api/test/**","/api/user/req/phone/otp", "/api/user/register"};


    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest()
//                        .authenticated())
//                .httpBasic(withDefaults())
//                .formLogin(withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//                //if csrf disable authenticationEntryPoint will throw if not accessDeniedHandler exception will throw
//                .exceptionHandling((ex)->
//                        ex.authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                                .accessDeniedHandler(customAccessDeniedHandler)
//                )
//
//        ;
//        return http.build();
//    }


    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {
        http
                //csrf disable
                .csrf(AbstractHttpConfigurer::disable)
                //open api
                .authorizeHttpRequests((a)-> a
                        .requestMatchers(openApi).permitAll()
                        .anyRequest().authenticated())

//                .formLogin((form)-> form.disable())
                .sessionManagement(sm-> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((ex)-> ex
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler));

        return http.build();

    }


//   @Bean
//    public WebSecurityCustomizer webSecurityCustomizer(){
//        //ignore folders to allow static resources to be released
//        return (web -> web.ignoring().requestMatchers("**/static/**", "static/js/**"));
//   }

}
