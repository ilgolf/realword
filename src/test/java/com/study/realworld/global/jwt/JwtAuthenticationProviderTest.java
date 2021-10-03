package com.study.realworld.global.jwt;

import com.study.realworld.domain.auth.infrastructure.TokenProvider;
import com.study.realworld.domain.user.application.UserFindService;
import com.study.realworld.domain.user.domain.*;
import com.study.realworld.global.jwt.authentication.JwtAuthentication;
import com.study.realworld.global.jwt.authentication.JwtAuthenticationProvider;
import com.study.realworld.global.security.error.exception.UserDetailsNullPointerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import static com.study.realworld.domain.auth.infrastructure.TokenProviderTest.testToken;
import static com.study.realworld.domain.user.domain.BioTest.BIO;
import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.ImageTest.IMAGE;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.domain.UserTest.userBuilder;
import static com.study.realworld.global.jwt.authentication.JwtAuthentication.initAuthentication;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationProviderTest {

    @Mock private TokenProvider tokenProvider;
    @Mock private UserFindService userFindService;
    @InjectMocks private JwtAuthenticationProvider jwtAuthenticationProvider;

    @DisplayName("JwtAuthenticationProvider 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(userFindService, tokenProvider);

        assertAll(
                () -> assertThat(jwtAuthenticationProvider).isNotNull(),
                () -> assertThat(jwtAuthenticationProvider).isInstanceOf(AuthenticationProvider.class),
                () -> assertThat(jwtAuthenticationProvider).isExactlyInstanceOf(JwtAuthenticationProvider.class)
        );
    }

    @DisplayName("JwtAuthenticationProvider 인스턴스 authenticate() 테스트")
    @Test
    void authenticate_test() {
        final Long principal = 1L;
        final Email email = new Email(EMAIL);
        final Password password = new Password(PASSWORD);
        final User user = userBuilder(email, new Name(USERNAME), password, new Bio(BIO), new Image(IMAGE));
        ReflectionTestUtils.setField(user, "id", 1L);
        doReturn(USERNAME).when(tokenProvider).mapToUsername(any());
        doReturn(user).when(userFindService).findUserByEmail(any());

        final JwtAuthentication jwtAuthentication = initAuthentication(testToken());
        final Authentication authenticate = jwtAuthenticationProvider.authenticate(jwtAuthentication);

        assertAll(
                () -> assertThat(authenticate).isNotNull(),
                () -> assertThat(authenticate.getPrincipal()).isEqualTo(principal),
                () -> assertThat(authenticate.getCredentials()).isEqualTo(password)
        );
    }

    @DisplayName("JwtAuthenticationProvider 인스턴스 supports() 테스트")
    @Test
    void supports_test() {
        final JwtAuthentication jwtAuthentication = JwtAuthentication.initAuthentication(testToken());
        final TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(USERNAME, PASSWORD);

        assertAll(
                () -> assertThat(jwtAuthenticationProvider.supports(jwtAuthentication.getClass())).isTrue(),
                () -> assertThat(jwtAuthenticationProvider.supports(testingAuthenticationToken.getClass())).isFalse()
        );
    }

    @DisplayName("JwtAuthenticationProvider 인스턴스 validateUserDetailsNull() 테스트")
    @Test
    void validateUserDetailsNull_test() {
        doReturn(USERNAME).when(tokenProvider).mapToUsername(any());
        doReturn(null).when(userFindService).findUserByEmail(any());

        final JwtAuthentication jwtAuthentication = initAuthentication(testToken());
        assertThatThrownBy(() -> jwtAuthenticationProvider.authenticate(jwtAuthenticationProvider.authenticate(jwtAuthentication)))
                .isInstanceOf(UserDetailsNullPointerException.class)
                .hasMessage("UserDetails is null");
    }

}