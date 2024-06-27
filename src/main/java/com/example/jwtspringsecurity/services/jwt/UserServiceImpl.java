package com.example.jwtspringsecurity.services.jwt;

//import com.example.jwtspringsecurity.enities.User;
import com.example.jwtspringsecurity.enities.UserRole;
import com.example.jwtspringsecurity.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo UserRepo;
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // viết logic để kế nối với người dùng trong db
        com.example.jwtspringsecurity.enities.User user = UserRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email không tồn tại: " + email));
// lấy tên quyền của user với name của entity ROLE tương ứng -> ánh xạ sang ROLE trong spring security thì lúc này code mới  hiểu được đó là role nào và phân quyền phạm vi truy cập cho người dùng
        var authorities = user.getUserRoles().stream()
                .map(UserRole::getRole)
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .map(role -> new SimpleGrantedAuthority(role.getName()))

                .collect(Collectors.toSet());

//        return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);

    }
}
