package com.lifesemantics.reservation.reservation.service;

import com.lifesemantics.reservation.common.Exception.CustomException;
import com.lifesemantics.reservation.common.util.ImageUtil;
import com.lifesemantics.reservation.hospital.dao.HospitalDAO;
import com.lifesemantics.reservation.hospital.dto.HospitalRequestDTO;
import com.lifesemantics.reservation.hospital.dto.HospitalResponseDTO;
import com.lifesemantics.reservation.reservation.dao.ReservationDAO;
import com.lifesemantics.reservation.reservation.dto.ReservationRequestDTO;
import com.lifesemantics.reservation.reservation.dto.ReservationResponseDTO;
import com.lifesemantics.reservation.reservation.dto.UpdateReservationRequestDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ReservationServiceImpl implements ReservationService{

    private ReservationDAO reservationDAO;
    private HospitalDAO hospitalDAO;

    @Value("${upload.path}")
    private String UPLOAD_PATH;
    @Autowired
    public ReservationServiceImpl(ReservationDAO reservationDAO, HospitalDAO hospitalDAO){
        this.reservationDAO = reservationDAO;
        this.hospitalDAO = hospitalDAO;
    }
    @Override
    @Transactional
    public int createReservation(int userIdx, ReservationRequestDTO req, MultipartFile image) throws IOException {
        HospitalRequestDTO hospitalReq = req.getHospitalReq();
        int dupl = reservationDAO.getDuplicatedReservation(userIdx, req.getReservationDate(), req.getHospitalIdx());
        if(dupl > 0){
            throw new CustomException("같은날 같은 병원에 이미 예약이 있습니다.",  HttpServletResponse.SC_BAD_REQUEST);
        }
        HospitalResponseDTO hospital = hospitalDAO.getHospital(req.getHospitalReq().getHospitalName(), req.getHospitalReq().getHospitalYkiho());
        if(hospital == null){
            int hospitalState = hospitalDAO.createHospital(hospitalReq);
            if(hospitalState < 1){
                throw new CustomException("병원에 대한 정보가 정상적으로 저장되지 않았습니다.", HttpServletResponse.SC_BAD_REQUEST);
            }
            req.setHospitalIdx(hospitalState);
        }else {
            req.setHospitalIdx(hospital.getHospitalIdx());
        }

        String savePath = "";
        if(image != null){
            savePath = ImageUtil.imageSave(UPLOAD_PATH, req.getHospitalIdx(), image);
        }
        int state = reservationDAO.createReservation(userIdx, req, savePath);
        if(state < 1){
            throw new CustomException("예약이 정상적으로 생성되지 않았습니다.", HttpServletResponse.SC_BAD_REQUEST);
        }


        return state;
    }

    @Override
    @Transactional
    public int updateReservation(int userIdx, int reservationIdx, UpdateReservationRequestDTO req, MultipartFile image) throws IOException{
        String savePath = "";
        int checkUser = reservationDAO.checkReservation(userIdx, reservationIdx);
        if(checkUser < 1){
            throw new CustomException("해당 게시물을 수정할 수 있는 권한이 없습니다.", HttpServletResponse.SC_FORBIDDEN);
        }

        if(image != null){
            savePath = ImageUtil.imageSave(UPLOAD_PATH, reservationIdx, image);
        }

        int state = reservationDAO.updateReservation(reservationIdx, req, savePath);

        if(state < 1){
            throw new CustomException("정상적으로 삭제되지 않았습니다.", HttpServletResponse.SC_BAD_REQUEST);
        }

        return state;
    }

    @Override
    @Transactional
    public int deleteReservation(int userIdx, int reservationIdx) {
        int checkUser = reservationDAO.checkReservation(userIdx, reservationIdx);
        if(checkUser < 1){
            throw new CustomException("해당 게시물을 삭제할 수 있는 권한이 없습니다.", HttpServletResponse.SC_FORBIDDEN);
        }
        int state =reservationDAO.deleteReservation(reservationIdx);
        if(state < 1){
            throw new CustomException("정상적으로 수정되지 않았습니다.", HttpServletResponse.SC_BAD_REQUEST);
        }
        return state;
    }

    @Override
    @Transactional
    public List<ReservationResponseDTO> getReservationList(int userIdx) {
        return reservationDAO.getReservationList(userIdx);
    }

    @Override
    @Transactional
    public ReservationResponseDTO getReservation(int userIdx, int reservationIdx) {
        ReservationResponseDTO res = reservationDAO.getReservation(userIdx, reservationIdx);

        if(res.getImage() != null)
        res.setImage(ImageUtil.getEncodePhotoPath(res.getImage()));
        return res;
    }
}
