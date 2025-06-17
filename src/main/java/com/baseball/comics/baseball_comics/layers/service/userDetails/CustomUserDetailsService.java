package com.baseball.comics.baseball_comics.layers.service.userDetails;

import com.baseball.comics.baseball_comics.layers.dto.userDetails.CustomUserDetails;
import com.baseball.comics.baseball_comics.layers.repository.User.UserEntity;
import com.baseball.comics.baseball_comics.layers.repository.User.UserJpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;

    public CustomUserDetailsService(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userData = userJpaRepository.findByUid(username);

        if (userData == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return new CustomUserDetails(userData);
    }
}
