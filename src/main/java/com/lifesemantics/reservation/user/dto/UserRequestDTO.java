package com.lifesemantics.reservation.user.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @NotBlank(message="이름을 입력해주세요.")
    String userName;
    @NotBlank(message="아이디를 입력해주세요.")
    String userId;
    @NotBlank(message="비밀번호를 입력해주세요.")
    String userPwd;

}
