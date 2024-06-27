package com.example.jwtspringsecurity.configuration;

import com.example.jwtspringsecurity.filters.JwtRequestFilter;
import com.example.jwtspringsecurity.services.jwt.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {
//@Autowired
//UserServiceImpl userDetailsServiceIm;
    private JwtRequestFilter jwtRequestFilter;

    public WebSecurityConfiguration(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(csrf -> csrf.disable())
//                .authorizeRequests(authorize -> authorize
//                        .requestMatchers("/login","/signup").permitAll()
//                        .and()
//                        .authorizeRequests().requestMatchers("/api/**").authenticated()
//                        .and()
//
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .build();
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/login","/signup").permitAll()
//
                        .requestMatchers("/api/**").authenticated()// yêu cầu người dùng phải xác thực = token
                        .requestMatchers("/api/user/**").hasRole("USER")
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .requestMatchers("/api/manager/**").hasRole("MANAGER")


                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    // mã hóa password
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//
//        authProvider.setUserDetailsService(userDetailsServiceIm);
//        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
//
//        return authProvider;
//    }

}
