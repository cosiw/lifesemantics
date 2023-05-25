package com.lifesemantics.reservation.reservation.dto;

import com.lifesemantics.reservation.hospital.dto.HospitalResponseDTO;
import com.lifesemantics.reservation.user.dto.UserResponseDTO;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationResponseDTO {

    private int reservationIdx;
    private String symptom;
    private String cellphone;
    private LocalDate reservationDate;
    private String image;
    private HospitalResponseDTO hospitalRes;
    private UserResponseDTO userRes;
    private String ykiho;
}
