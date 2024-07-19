package com.example.jwtspringsecurity.Mapper;

import com.example.jwtspringsecurity.dto.RegisterTimeDTO;
import com.example.jwtspringsecurity.enities.RegisterTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")

public interface RegisterTimeMapper {
    @Mappings({
            @Mapping(source = "date", target = "date"),
            @Mapping(source = "checkIn", target = "checkIn"),
            @Mapping(source = "checkOut", target = "checkOut"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "requestTime",target = "requestTime")
    })
    RegisterTime REGISTER_TIME_DTO_TO_REGISTER_TIME(RegisterTimeDTO registerTimeDTO);
    @Mappings({
            @Mapping(source = "date", target = "date"),
            @Mapping(source = "checkIn", target = "checkIn"),
            @Mapping(source = "checkOut", target = "checkOut"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "requestTime",target = "requestTime")
    })
    RegisterTimeDTO REGISTER_TIME_TO_REGISTER_TIME_DTO(RegisterTime registerTime);
}
