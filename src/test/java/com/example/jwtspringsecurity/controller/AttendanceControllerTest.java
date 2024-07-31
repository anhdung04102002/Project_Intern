package com.example.jwtspringsecurity.controller.User;

import com.example.jwtspringsecurity.dto.AttendanceDTO;
import com.example.jwtspringsecurity.dto.AttendanceRequestDto;
import com.example.jwtspringsecurity.services.UserService.AttendanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AttendanceController.class)
public class AttendanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttendanceService attendanceService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testProcessAttendanceToday() throws Exception {
        // Given
        AttendanceRequestDto attendanceRequestDto = new AttendanceRequestDto();
//        attendanceRequestDto.setDate(LocalDate.of(2024, 7, 29));
        attendanceRequestDto.setCheckIn(LocalTime.of(8, 35));
        attendanceRequestDto.setCheckOut(LocalTime.of(17, 31));

        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setDate(LocalDate.of(2024, 7, 29));
        attendanceDTO.setCheckIn(LocalTime.of(8, 35));
        attendanceDTO.setCheckOut(LocalTime.of(17, 31));
        attendanceDTO.setCheckInLate(0);
        attendanceDTO.setCheckOutEarly(0);
        attendanceDTO.setPunishmentMoney(0);
        attendanceDTO.setComplain(null);
        attendanceDTO.setComplainReply(null);

        given(attendanceService.ProcessAttendanceToday(attendanceRequestDto)).willReturn(attendanceDTO);

        // When & Then
        mockMvc.perform(post("/api/user/attendance/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(attendanceRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date", is("2024-07-29")))
                .andExpect(jsonPath("$.checkIn", is("08:35")))
                .andExpect(jsonPath("$.checkOut", is("17:31")))
                .andExpect(jsonPath("$.checkInLate", is(0)))
                .andExpect(jsonPath("$.checkOutEarly", is(0)))
                .andExpect(jsonPath("$.punishmentMoney", is(0)))
                .andExpect(jsonPath("$.complain").doesNotExist())
                .andExpect(jsonPath("$.complainReply").doesNotExist());
    }
}
