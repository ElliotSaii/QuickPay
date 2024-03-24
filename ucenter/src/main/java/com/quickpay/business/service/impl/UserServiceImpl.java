package com.quickpay.business.service.impl;
import com.quickpay.business.entity.User;
import com.quickpay.business.repository.UserRepository;
import com.quickpay.business.service.UserService;
import com.quickpay.business.vo.LoginVo;
import com.quickpay.business.vo.RegisterVo;
import com.quickpay.commons.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    private  final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByPhone(String phone) {
        return userRepository.getUserByPhone(phone);
    }

    @Override
    public void doLogin(String otp) {
        //todo check otp from redis db

    }

    @Override
    public String getOtpByPhone(String phone) {
        User user = userRepository.getUserByPhone(phone);
        if(user == null){
            throw new BusinessException("no associated with this phone "+phone);
        }
        //todo generate actual otp and store them in redis db expire in 1 min

        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);

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
