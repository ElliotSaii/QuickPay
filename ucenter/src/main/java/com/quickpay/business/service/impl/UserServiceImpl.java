package com.quickpay.business.service.impl;
import com.quickpay.business.entity.User;
import com.quickpay.business.repository.UserRepository;
import com.quickpay.business.service.UserService;
import com.quickpay.business.vo.RegisterVo;
import com.quickpay.commons.exceptions.AuthorizeException;
import com.quickpay.commons.exceptions.BusinessException;
import com.quickpay.commons.manager.CacheManager;
import com.quickpay.commons.utils.CacheUtils;
import com.quickpay.commons.utils.RandomUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private  final UserRepository userRepository;
    private final CacheManager cacheManager;
    public UserServiceImpl(UserRepository userRepository, CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.cacheManager = cacheManager;
    }

    @Override
    public User getUserByPhone(String phone) {
        return userRepository.getUserByPhone(phone);
    }

    @Override
    public void doLogin(String otp) {
      boolean isValidated = cacheManager.isValidPhoneOtp(otp);
      if(!isValidated){
          throw new AuthorizeException(HttpStatus.UNAUTHORIZED.value(), "Unauthorized user");
      }
      
    }

    @Override
    public String getOtpByPhone(String phone) {
        User user = userRepository.getUserByPhone(phone);
        if(user == null){
            throw new BusinessException("no associated with this phone "+phone);
        }
        String otp = RandomUtils.randomNum(6);
        cacheManager.cacheOtp(otp);
        return  otp;

    }


    @Override
    public User getByUserId(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new BusinessException(400, "user not found with id " + id));
    }

    @Override
    public void doRegister(RegisterVo registerVo) {
        User isExistedUser = userRepository.getUserByPhone(registerVo.getPhone());
        if (isExistedUser != null) {
            throw new BusinessException(400, "user already existed ");
        }
        userRepository.save(User.builder()
                .firstName(registerVo.getFirstName())
                .lastName(registerVo.getLastName())
                .phone(registerVo.getPhone())
                .createdAt(new Date())
                .build()
        );
    }
}
