package com.lifesemantics.reservation.reservation.dao;

import com.lifesemantics.reservation.reservation.dto.ReservationRequestDTO;
import com.lifesemantics.reservation.reservation.dto.ReservationResponseDTO;
import com.lifesemantics.reservation.reservation.dto.UpdateReservationRequestDTO;
import com.lifesemantics.reservation.reservation.mapper.ReservationMapper;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDAOImpl implements ReservationDAO{

    private ReservationMapper reservationMapper;
    @Autowired
    public ReservationDAOImpl(ReservationMapper reservationMapper){
        this.reservationMapper = reservationMapper;
    }

    @Override
    public int createReservation(int userIdx, ReservationRequestDTO reservationReq, String savePath) {
        return reservationMapper.createReservation(userIdx, reservationReq, savePath);
    }

    @Override
    public int updateReservation(int reservationIdx, UpdateReservationRequestDTO req, String savePath) {
        return reservationMapper.updateReservation(reservationIdx,req, savePath);
    }

    @Override
    public int deleteReservation(int reservationIdx) {
        return reservationMapper.deleteReservation(reservationIdx);
    }

    @Override
    public List<ReservationResponseDTO> getReservationList(int userIdx) {

        return reservationMapper.getReservationList(userIdx);
    }

    @Override
    public ReservationResponseDTO getReservation(int userIdx, int reservationIdx) {

        return reservationMapper.getReservation(userIdx, reservationIdx);
    }

    @Override
    public int getDuplicatedReservation(int userIdx, LocalDate reservationDate, int hospitalIdx) {
        return reservationMapper.getDuplicatedReservation(userIdx, reservationDate, hospitalIdx);
    }

    @Override
    public int checkReservation(int userIdx, int reservationIdx) {
        return reservationMapper.checkReservation(userIdx, reservationIdx);
    }
}
