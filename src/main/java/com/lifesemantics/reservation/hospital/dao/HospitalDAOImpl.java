package com.lifesemantics.reservation.hospital.dao;

import com.lifesemantics.reservation.hospital.dto.HospitalRequestDTO;
import com.lifesemantics.reservation.hospital.dto.HospitalResponseDTO;
import com.lifesemantics.reservation.hospital.mapper.HospitalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HospitalDAOImpl implements HospitalDAO{

    private HospitalMapper hospitalMapper;

    @Autowired
    public HospitalDAOImpl(HospitalMapper hospitalMapper){
        this.hospitalMapper = hospitalMapper;
    }
    @Override
    public int createHospital(HospitalRequestDTO req) {

        int state = hospitalMapper.createHospital(req);

        if(state > 0){
            return req.getHospitalIdx();
        } else {
            return 0;
        }

    }

    @Override
    public HospitalResponseDTO getHospital(String hospitalName, String hospitalYkiho) {

        return hospitalMapper.findHospital(hospitalName, hospitalYkiho);
    }


}
