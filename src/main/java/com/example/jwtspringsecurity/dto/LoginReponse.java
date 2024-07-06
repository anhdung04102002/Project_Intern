package com.example.jwtspringsecurity.dto;

import java.util.List;

//public record LoginResponse(String jwt) {
//}
public class LoginReponse {
    private String jwtToken;
    private String refreshToken;
    private String roles;
//    public LoginReponse(String jwtToken) {
//        this.jwtToken = jwtToken;
//    }
public LoginReponse(String jwtToken, String refreshToken,String roles) {
    this.jwtToken = jwtToken;
    this.refreshToken = refreshToken;
    this.roles = roles;
}
    public LoginReponse(String jwtToken,String roles) {
        this.jwtToken = jwtToken;
        this.roles = roles;
    }


    public String getRefreshToken() {
        return refreshToken;
    }

    // phải thêm getter để phục vụ  việc json truy caập vào trường này
    public String getJwtToken() {
        return jwtToken;
    }
    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
//    Khi  trả về một đối tượng LoginReponse từ controller , Jackson sẽ sử dụng các getter để lấy giá trị của jwtToken và roles và tuần tự hóa chúng thành JSON.
}
