package com.study.realworld.domain.user.domain.persist;

import com.study.realworld.domain.user.domain.vo.*;
import com.study.realworld.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {

    public static final String DEFAULT_AUTHORITY = "USER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false, updatable = false)
    private Long id;

    @Embedded
    private UserEmail userEmail;

    @Embedded
    private UserName userName;

    @Embedded
    private UserPassword userPassword;

    @Embedded
    private UserBio userBio;

    @Embedded
    private UserImage userImage;

    @Builder
    public User(final UserEmail userEmail, final UserName userName, final UserPassword userPassword, final UserBio userBio, final UserImage userImage) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userBio = userBio;
        this.userImage = userImage;
    }

    public void encode(final PasswordEncoder passwordEncoder) {
        userPassword = UserPassword.encode(userPassword.value(), passwordEncoder);
    }

    public User login(final UserPassword rawUserPassword, final PasswordEncoder passwordEncoder) {
        userPassword.matches(rawUserPassword, passwordEncoder);
        return this;
    }

    public User changeEmail(final UserEmail userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public User changeBio(final UserBio userBio) {
        this.userBio = userBio;
        return this;
    }

    public User changeImage(final UserImage userImage) {
        this.userImage = userImage;
        return this;
    }

    public UserEmail userEmail() {
        return userEmail;
    }

    public UserName userName() {
        return userName;
    }

    public UserBio userBio() {
        return userBio;
    }

    public UserImage userImage() {
        return userImage;
    }

    public UserPassword userPassword() {
        return userPassword;
    }

}