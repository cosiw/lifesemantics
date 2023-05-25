package com.lifesemantics.reservation.user.dao;

import com.lifesemantics.reservation.user.dto.LoginRequestDTO;
import com.lifesemantics.reservation.user.dto.UserRequestDTO;
import com.lifesemantics.reservation.user.dto.UserResponseDTO;

public interface UserDAO {

    UserResponseDTO login(LoginRequestDTO req);
    int join(UserRequestDTO req);
    public boolean findByUserId(String userId);

    UserResponseDTO findByUserIdx(int userIdx);
}
