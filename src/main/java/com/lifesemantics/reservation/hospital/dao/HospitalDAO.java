package com.lifesemantics.reservation.hospital.dao;

import com.lifesemantics.reservation.hospital.dto.HospitalRequestDTO;
import com.lifesemantics.reservation.hospital.dto.HospitalResponseDTO;
import org.springframework.stereotype.Repository;


public interface HospitalDAO {
    int createHospital(HospitalRequestDTO req);
    HospitalResponseDTO getHospital(String hospitalName, String hospitalYkiho);
}
