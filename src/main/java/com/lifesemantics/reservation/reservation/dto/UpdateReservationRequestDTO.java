package com.lifesemantics.reservation.reservation.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateReservationRequestDTO {

    private String symptom;
    private String cellphone;
    private LocalDate reservationDate;
}
