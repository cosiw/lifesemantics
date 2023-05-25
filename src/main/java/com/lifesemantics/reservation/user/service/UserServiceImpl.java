package com.lifesemantics.reservation.user.service;

import com.lifesemantics.reservation.common.Exception.CustomException;
import com.lifesemantics.reservation.common.jwt.JwtTokenProvider;
import com.lifesemantics.reservation.user.dao.UserDAO;
import com.lifesemantics.reservation.user.dto.LoginRequestDTO;
import com.lifesemantics.reservation.user.dto.UserRequestDTO;
import com.lifesemantics.reservation.user.dto.UserResponseDTO;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private UserResponseDTO responseDTO;
    private JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserDAO userDAO, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder){
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    @Transactional
    public UserResponseDTO login(LoginRequestDTO req) {

        UserResponseDTO user = userDAO.login(req);

        if(ObjectUtils.isEmpty(user)){
            throw new CustomException("아이디와 비밀번호를 다시 확인해 주세요.", HttpServletResponse.SC_BAD_REQUEST);
        }
        if(!passwordEncoder.matches(req.getUserPwd(), user.getUserPwd())){
            throw new CustomException("비밀번호를 다시 확인해 주세요.", HttpServletResponse.SC_BAD_REQUEST);
        }
        user.setUserPwd("");
        user.setAccessToken(jwtTokenProvider.createToken(String.valueOf(user.getUserIdx())));

        return user;
    }

    @Override
    @Transactional
    public int join(UserRequestDTO req) {
        if(userDAO.findByUserId(req.getUserId())) {
            throw new CustomException("아이디가 중복됩니다.", HttpServletResponse.SC_BAD_REQUEST);
        }
        req.setUserPwd(passwordEncoder.encode(req.getUserPwd()));

        int state = userDAO.join(req);
        return state;
    }
}
