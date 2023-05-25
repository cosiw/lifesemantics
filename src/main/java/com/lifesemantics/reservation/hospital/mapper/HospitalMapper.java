package com.lifesemantics.reservation.hospital.mapper;

import com.lifesemantics.reservation.hospital.dto.HospitalRequestDTO;
import com.lifesemantics.reservation.hospital.dto.HospitalResponseDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HospitalMapper {

    @Insert("Insert into thospital (hospital_name, hospital_addr, hospital_tel, hospital_ykiho) "
        + "values (#{hospitalName}, #{hospitalAddr}, #{hospitalTel}, #{hospitalYkiho})")
    @Options(useGeneratedKeys = true, keyProperty = "hospitalIdx")
    int createHospital(HospitalRequestDTO req);

    @Select("select * from thospital where hospital_ykiho = #{hospitalYkiho} and hospital_name = #{hospitalName} LIMIT 1")
    HospitalResponseDTO findHospital(@Param("hospitalName") String hospitalName, @Param("hospitalYkiho") String hospitalYkiho);
}
