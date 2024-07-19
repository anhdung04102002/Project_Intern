package com.example.jwtspringsecurity.Mapper;

import com.example.jwtspringsecurity.dto.AttendanceDTO;
import com.example.jwtspringsecurity.dto.AttendanceRequestDto;
import com.example.jwtspringsecurity.enities.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")

public interface AttendanceMapper {
    @Mappings({
            @Mapping(source = "date", target = "date"),
            @Mapping(source = "checkIn", target = "checkIn"),
            @Mapping(source = "checkOut", target = "checkOut"),
            @Mapping(source = "complain", target = "complain"),
            @Mapping(source = "complainReply", target = "complainReply"),
            @Mapping(source = "punishmentMoney", target = "punishmentMoney"),
            @Mapping(source = "checkOutEarly", target = "checkOutEarly"),
            @Mapping(source = "checkInLate",target = "checkInLate")
    })
    Attendance ATTENDANCE_DTO_TO_ATTENDANCE(AttendanceDTO attendanceDTO);

    @Mappings({
            @Mapping(source = "date", target = "date"),
            @Mapping(source = "checkIn", target = "checkIn"),
            @Mapping(source = "checkOut", target = "checkOut"),
            @Mapping(source = "complain", target = "complain"),
            @Mapping(source = "complainReply", target = "complainReply"),
            @Mapping(source = "punishmentMoney", target = "punishmentMoney"),
            @Mapping(source = "checkOutEarly", target = "checkOutEarly"),
            @Mapping(source = "checkInLate",target = "checkInLate")
    })
    AttendanceDTO ATTENDANCE_TO_ATTENDANCE_DTO(Attendance attendance);
    @Mappings({
            @Mapping(source = "checkIn", target = "checkIn"),
            @Mapping(source = "checkOut", target = "checkOut")
    })
    Attendance toAttendance(AttendanceRequestDto attendanceRequestDTO);
}
