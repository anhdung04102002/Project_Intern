package com.example.jwtspringsecurity.enities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference("branch-user") // phải đặt tên
    private List<User> users;
}
