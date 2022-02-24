package ex.travel.controller;

import ex.travel.domain.User;
import ex.travel.repository.UserRepository;
import ex.travel.service.CustomUserDetailService;
import ex.travel.springSecurity.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final CustomUserDetailService customUserDetailService;

    //회원가입
    @PostMapping("/join")
    public UpdateMemberResponse join(@RequestBody MemberForm userForm) {
        Long id = customUserDetailService.join(User.builder()
                .email(userForm.getEmail())
                .password(passwordEncoder.encode(userForm.getPassword()))
                .name(userForm.getName())
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        User find = userRepository.findById(id).get();
        return new UpdateMemberResponse(find.getEmail(), find.getPassword(), find.getName());

    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private String email;
        private String password;
        private String name;
    }

    @Data
    static class MemberForm {
        private String email;
        private String password;
        private String name;
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }
}
