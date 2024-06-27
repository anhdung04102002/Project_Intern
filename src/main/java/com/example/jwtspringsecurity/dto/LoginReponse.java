package com.example.jwtspringsecurity.dto;
//public record LoginResponse(String jwt) {
//}
public class LoginReponse {
    private String jwtToken;

    public LoginReponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }
// phải thêm getter để phục vụ  việc json truy caập vào trường này
    public String getJwtToken() {
        return jwtToken;
    }
}
