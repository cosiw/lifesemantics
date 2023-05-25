package com.lifesemantics.reservation.reservation.service;

import com.lifesemantics.reservation.reservation.dto.ReservationRequestDTO;
import com.lifesemantics.reservation.reservation.dto.ReservationResponseDTO;
import com.lifesemantics.reservation.reservation.dto.UpdateReservationRequestDTO;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ReservationService {

    int createReservation(int userIdx, ReservationRequestDTO req, MultipartFile image)
        throws IOException;
    int updateReservation(int userIdx, int reservationIdx, UpdateReservationRequestDTO req, MultipartFile image) throws IOException;
    int deleteReservation(int userIdx, int reservationIdx);
    List<ReservationResponseDTO> getReservationList(int userIdx);
    ReservationResponseDTO getReservation(int userIdx, int reservationIdx);

}
