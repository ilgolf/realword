package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.domain.UserRepository;
import com.study.realworld.domain.user.dto.UserJoinRequest;
import com.study.realworld.domain.user.dto.UserJoinResponse;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class UserJoinService {

    private final UserRepository userRepository;

    public UserJoinService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Valid
    public UserJoinResponse join(final UserJoinRequest userJoinRequest) {
        final User user = userRepository.save(userJoinRequest.toUser());
        return UserJoinResponse.fromUser(user);
    }

}
