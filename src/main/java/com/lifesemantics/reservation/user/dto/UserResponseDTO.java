package com.lifesemantics.reservation.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

    private int userIdx;
    private String userId;
    private String userName;
    private String userPwd;
    private String accessToken;
}
