package com.example.jwtspringsecurity.enities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "branch")
@Data
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String displayName;

    @Enumerated(EnumType.STRING)   // đánh dấu enum là dạng String lưu cơ sở dữ liệu dưới dạng chuỗi
    private WorkingTime workingTime;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;
}
