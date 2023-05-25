package com.lifesemantics.reservation.reservation.dto;

import com.lifesemantics.reservation.hospital.dto.HospitalRequestDTO;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRequestDTO {

    private String symptom;
    private LocalDate reservationDate;
    private String image;
    private String cellphone;
    private HospitalRequestDTO hospitalReq;
    private int hospitalIdx;

}
