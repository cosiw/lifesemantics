package com.lifesemantics.reservation.user.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    @NotBlank(message="아이디를 입력해주세요.")
    private String userId;
    @NotBlank(message="비밀번호를 입력해주세요.")
    private String userPwd;
}
