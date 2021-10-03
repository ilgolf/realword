package com.study.realworld.domain.follow.domain;

import com.study.realworld.domain.user.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.study.realworld.domain.user.domain.BioTest.BIO;
import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.ImageTest.IMAGE;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.domain.UserTest.userBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FollowTest {

    @DisplayName("Follow 인스턴스 기본 생성자 테스트")
    @Test
    void constructor_test() {
        final Follow follow = new Follow();

        assertAll(
                () -> assertThat(follow).isNotNull(),
                () -> assertThat(follow).isExactlyInstanceOf(Follow.class)
        );
    }

    @DisplayName("Follow 인스턴스 빌더 테스트")
    @Test
    void builder_test() {
        final User user1 = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));
        final User user2 = userBuilder(new Email("Email2@email.com"), new Name("differentUserName"), new Password("Password2"), new Bio("Bio2"), new Image("Image2"));

        final Follow follow = new Follow.Builder()
                .following(user1)
                .follower(user2)
                .build();

        assertAll(
                () -> assertThat(follow).isNotNull(),
                () -> assertThat(follow).isExactlyInstanceOf(Follow.class)
        );
    }


}