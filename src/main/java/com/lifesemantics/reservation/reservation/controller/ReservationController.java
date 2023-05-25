package com.lifesemantics.reservation.reservation.controller;

import com.lifesemantics.reservation.common.resolver.LoginIdx;
import com.lifesemantics.reservation.common.util.ImageUtil;
import com.lifesemantics.reservation.reservation.dto.ReservationRequestDTO;
import com.lifesemantics.reservation.reservation.dto.ReservationResponseDTO;
import com.lifesemantics.reservation.reservation.dto.UpdateReservationRequestDTO;
import com.lifesemantics.reservation.reservation.service.ReservationService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("")
public class ReservationController {

    private ReservationService reservationService;
    @Autowired
    public ReservationController(ReservationService reservationService){
        this.reservationService=reservationService;
    }
    @Value("${upload.path}")
    private String UPLOAD_PATH;

    @PostMapping(value="/login/api/reservation",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Integer> createReservation(@LoginIdx Integer userIdx,
        @RequestPart(required = false) ReservationRequestDTO ReservationReq,
        @RequestPart(required = false) MultipartFile image) throws IOException {

        System.out.println("date : " + ReservationReq.getReservationDate());
        ReservationReq.setReservationDate(ReservationReq.getReservationDate());
        int res = reservationService.createReservation(userIdx, ReservationReq, image);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping(value="/login/api/reservation/{reservationIdx}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Integer> updateReservation(@LoginIdx Integer userIdx,
        @RequestPart(required = false) UpdateReservationRequestDTO req,
        @RequestPart(required = false) MultipartFile image,
        @PathVariable int reservationIdx) throws IOException{
        System.out.println(req.getReservationDate());
        int res = reservationService.updateReservation(userIdx, reservationIdx, req, image);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/login/api/reservation/{reservationIdx}")
    public ResponseEntity<Integer> deleteReservation(@LoginIdx Integer userIdx, @PathVariable int reservationIdx){
        int res =reservationService.deleteReservation(userIdx, reservationIdx);

        return new ResponseEntity(res, HttpStatus.OK);
    }
    @GetMapping("/login/api/reservation/{reservationIdx}")
    public ResponseEntity<ReservationResponseDTO> getReservation(@LoginIdx Integer userIdx, @PathVariable int reservationIdx){
        ReservationResponseDTO res = reservationService.getReservation(userIdx, reservationIdx);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/login/api/reservation")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationList(@LoginIdx Integer userIdx){

        List<ReservationResponseDTO> res = reservationService.getReservationList(userIdx);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/api/reservation/image/{encodePath}")
    public UrlResource reservationPhoto(@PathVariable String encodePath) throws MalformedURLException{
        String decodePhotoPath = ImageUtil.getDecodePhotoPath(encodePath);
        String res = Paths.get(UPLOAD_PATH, decodePhotoPath).toString();
        System.out.println(res);
        return new UrlResource("file:" + res);
    }

}
