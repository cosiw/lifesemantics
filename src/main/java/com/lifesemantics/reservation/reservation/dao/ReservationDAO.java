package com.lifesemantics.reservation.reservation.dao;

import com.lifesemantics.reservation.reservation.dto.ReservationRequestDTO;
import com.lifesemantics.reservation.reservation.dto.ReservationResponseDTO;
import com.lifesemantics.reservation.reservation.dto.UpdateReservationRequestDTO;
import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {

    int createReservation(int userIdx, ReservationRequestDTO req, String savePath);
    int updateReservation(int reservationId, UpdateReservationRequestDTO req, String savePath);
    int deleteReservation(int reservationId);
    List<ReservationResponseDTO> getReservationList(int userIdx);
    ReservationResponseDTO getReservation(int userIdx, int reservationId);

    int getDuplicatedReservation(int userIdx, LocalDate reservationDate, int hospitalIdx);
    int checkReservation(int userIdx, int reservationIdx);
}
