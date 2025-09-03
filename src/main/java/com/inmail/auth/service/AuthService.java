package com.inmail.auth.service;

import com.inmail.auth.dto.SignupRequest;
import com.inmail.auth.dto.UserSummary;
import com.inmail.user.domain.User;
import com.inmail.user.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional // 여러 DB작업을 하나의 논리적인 단위로 바꿔줌
    public UserSummary signup(SignupRequest req) {
        // 중복 이메일 체크
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 비밀번호 해시화
        String hash = passwordEncoder.encode(req.getPassword());

        // 저장
        User saved = userRepository.save(new User(req.getEmail(), hash, req.getNickname()));

        // 반환용 요약
        return new UserSummary(saved.getId(), saved.getEmail(), saved.getNickname());
    }
}
