package com.lifesemantics.reservation.reservation.mapper;

import com.lifesemantics.reservation.reservation.dto.ReservationRequestDTO;
import com.lifesemantics.reservation.reservation.dto.ReservationResponseDTO;
import com.lifesemantics.reservation.reservation.dto.UpdateReservationRequestDTO;
import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ReservationMapper {

    @Insert("Insert into treservation (hospital_idx, symptom, reservation_date, image, cellphone, user_idx) "
        + "value (#{req.hospitalIdx}, #{req.symptom}, #{req.reservationDate}, #{savePath}, #{req.cellphone}, #{userIdx})")
    int createReservation(@Param("userIdx")int userIdx, @Param("req") ReservationRequestDTO req, @Param("savePath") String savePath);

    @Update("UPDATE treservation set symptom = #{req.symptom}, cellphone = #{req.cellphone}, reservation_date = #{req.reservationDate}, image = #{savePath}"
        + " WHERE reservation_idx = #{reservationIdx}")
    int updateReservation(@Param("reservationIdx") int reservationIdx, @Param("req") UpdateReservationRequestDTO req, @Param("savePath") String savePath);

    @Delete("DELETE FROM treservation WHERE reservation_idx= #{reservationIdx}")
    int deleteReservation(int reservationIdx);

    @Select("Select * FROM treservation r join thospital h on (r.hospital_idx = h.hospital_idx) WHERE user_idx= #{userIdx} ORDER BY reservation_date DESC")
    @Results(id="hospitalMap", value = {
        @Result(property = "reservationIdx", column = "reservation_idx"),
        @Result(property = "hospitalRes.hospitalIdx", column = "hospital_idx"),
        @Result(property = "hospitalRes.hospitalAddr", column = "hospital_addr"),
        @Result(property = "hospitalRes.hospitalName", column = "hospital_name"),
        @Result(property = "hospitalRes.hospitalTel", column = "hospital_tel"),
        @Result(property = "hospitalRes.hospitalYkiho", column = "hospital_ykiho"),
        @Result(property = "hospitalRes.hospitalDgsbjt", column = "hospital_dgsbjt")
    })
    List<ReservationResponseDTO> getReservationList(int userIdx);
    @Select("Select * FROM treservation r "
        + "join thospital h on (r.hospital_idx = h.hospital_idx) "
        + "join tuser u on (r.user_idx = u.user_idx) "
        +"WHERE reservation_idx = #{reservationIdx} AND u.user_idx= #{userIdx}")
    @Results(id="reservationMap", value = {
        @Result(property = "reservationIdx", column = "reservation_idx"),
        @Result(property = "hospitalRes.hospitalIdx", column = "hospital_idx"),
        @Result(property = "hospitalRes.hospitalAddr", column = "hospital_addr"),
        @Result(property = "hospitalRes.hospitalName", column = "hospital_name"),
        @Result(property = "hospitalRes.hospitalTel", column = "hospital_tel"),
        @Result(property = "hospitalRes.hospitalYkiho", column = "hospital_ykiho"),
        @Result(property = "hospitalRes.hospitalDgsbjt", column = "hospital_dgsbjt"),
        @Result(property = "userRes.userIdx", column = "user_idx"),
        @Result(property = "userRes.userName", column = "user_name"),
        @Result(property = "userRes.userId", column = "user_id")
    })
    ReservationResponseDTO getReservation(@Param("userIdx")int userIdx, @Param("reservationIdx")int reservationIdx);

    @Select("Select Count(*) from treservation WHERE user_idx = #{userIdx} AND reservation_date = #{reservationDate} AND hospital_idx = #{hospitalIdx}")
    int getDuplicatedReservation(@Param("userIdx")int userIdx, @Param("reservationDate")LocalDate reservationDate, @Param("hospitalIdx")int hospitalIdx);

    @Select("Select Count(*) from treservation WHERE user_idx= #{userIdx} AND reservation_idx=#{reservationIdx}")
    int checkReservation(@Param("userIdx") int userIdx, @Param("reservationIdx") int reservationIdx);


}
