package com.quickpay.commons.security;

import com.quickpay.business.entity.User;
import com.quickpay.business.service.UserService;
import com.quickpay.commons.exceptions.BusinessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = userService.getUserByPhone(phone);
          if(user == null){
              throw new BusinessException(400, "User not exist");
          }
        return new AppUser(user.getId() , getUserRole(user.getId()));
    }

    private List<GrantedAuthority> getUserRole(Long id) {
       User user =   userService.getByUserId(id);
       return AuthorityUtils.createAuthorityList("ROLE_USER");
    }
}
