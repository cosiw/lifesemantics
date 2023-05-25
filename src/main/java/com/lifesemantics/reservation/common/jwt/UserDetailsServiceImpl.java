package com.lifesemantics.reservation.common.jwt;

import com.lifesemantics.reservation.user.dao.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDAO userDAO;

    @Autowired
    public UserDetailsServiceImpl(UserDAO userDAO){
        this.userDAO = userDAO;
    }
    @Override
    public UserDetails loadUserByUsername(String userIdx) throws UsernameNotFoundException {

        return new CustomUserDetails(userDAO.findByUserIdx(Integer.parseInt(userIdx)));


    }
}
