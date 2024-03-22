package com.quickpay.business.service.impl;

import com.quickpay.business.repository.UserRepository;
import com.quickpay.business.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private  final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
