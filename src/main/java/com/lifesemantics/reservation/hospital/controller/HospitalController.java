package com.lifesemantics.reservation.hospital.controller;

import com.lifesemantics.reservation.common.resolver.LoginIdx;
import com.lifesemantics.reservation.common.util.HospitalHandler;
import java.io.IOException;
import java.net.URLEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HospitalController {

    HospitalHandler hospitalHandler = new HospitalHandler();
    @GetMapping("/login/api/hospital")
    public ResponseEntity<String> getHospitalList(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) String hospitalName) throws IOException {
        String encodeHospitalName = "";
        String hospitalNameStr = "";
        boolean flag = false;
        if(hospitalName != null){
            encodeHospitalName = URLEncoder.encode(hospitalName);
            hospitalNameStr = "&yadmNm=" + encodeHospitalName;
        }
            String urlstr = "https://apis.data.go.kr/B551182/hospInfoServicev2/getHospBasisList?" +
                "ServiceKey=vJGGK%2FahEQMRiY6ABkNX3gNYUgdM3XSzlQBZ057bQmAiEJfFkRnFRepGkq0K9VQpTerm%2BO7IR7Bk1P%2BGS4bYDQ%3D%3D" +
                "&pageNo=" + page +
                "&numOfRows=" + size +
                hospitalNameStr;


        String res = hospitalHandler.findResult(urlstr);
        return new ResponseEntity<>(res, HttpStatus.OK);

    }
    @GetMapping("/login/api/hospital/{hospitalName}/{ykiho}")
    public ResponseEntity<String> getHospital(@LoginIdx int userIdx, @PathVariable String hospitalName, @PathVariable String ykiho) throws IOException{

        String encodeHospitalName = URLEncoder.encode(hospitalName);
        String urlstr = "https://apis.data.go.kr/B551182/hospInfoServicev2/getHospBasisList?" +
            "ServiceKey=vJGGK%2FahEQMRiY6ABkNX3gNYUgdM3XSzlQBZ057bQmAiEJfFkRnFRepGkq0K9VQpTerm%2BO7IR7Bk1P%2BGS4bYDQ%3D%3D" +
            "&yadmNm=" + encodeHospitalName +
            "&ykiho=" + ykiho +
            "&type=xml";

        String res = hospitalHandler.findResult(urlstr);

        return new ResponseEntity<>(res, HttpStatus.OK);

    }




}
