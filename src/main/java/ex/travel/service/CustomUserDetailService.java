package ex.travel.service;

import ex.travel.domain.User;
import ex.travel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    //회원가입
    @Transactional(readOnly = false)
    public Long join(User user) {
        validateDuplicateMember(user); //중복 회원검증
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateMember(User user) {
        Optional<User> find = userRepository.findByEmail(user.getEmail());
        if(find.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

}
