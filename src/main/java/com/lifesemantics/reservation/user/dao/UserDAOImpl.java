package com.lifesemantics.reservation.user.dao;

import com.lifesemantics.reservation.common.Exception.CustomException;
import com.lifesemantics.reservation.user.dto.LoginRequestDTO;
import com.lifesemantics.reservation.user.dto.UserRequestDTO;
import com.lifesemantics.reservation.user.dto.UserResponseDTO;
import com.lifesemantics.reservation.user.mapper.UserMapper;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO{

    private UserMapper userMapper;
    @Autowired
    public UserDAOImpl(UserMapper userMapper){
        this.userMapper = userMapper;
    }
    @Override
    public UserResponseDTO login(LoginRequestDTO req) {
        return userMapper.login(req);
    }

    @Override
    public int join(UserRequestDTO req) {
        if(findByUserId(req.getUserId())){
            throw new CustomException("아이디가 중복됩니다.", HttpServletResponse.SC_BAD_REQUEST);
        }else {
            int res = userMapper.join(req);
            return res;
        }
    }
    @Override
    public boolean findByUserId(String userId){
        int state = userMapper.findByUserId(userId);
        if(state > 0){
           return true;
        }
        return false;
    }

    @Override
    public UserResponseDTO findByUserIdx(int userIdx) {
        UserResponseDTO user = userMapper.findByUserIdx(userIdx);

        return user;
    }
}
