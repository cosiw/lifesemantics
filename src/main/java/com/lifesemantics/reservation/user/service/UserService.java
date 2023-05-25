package com.lifesemantics.reservation.user.service;

import com.lifesemantics.reservation.user.dto.LoginRequestDTO;
import com.lifesemantics.reservation.user.dto.UserRequestDTO;
import com.lifesemantics.reservation.user.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO login(LoginRequestDTO req);
    int join(UserRequestDTO req);
}
