package com.jaeyeonling.ladder;

import com.jaeyeonling.ladder.exception.EmptyUsernameException;
import com.jaeyeonling.ladder.exception.LongerThanMaxLengthUsernameException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class UsernameTest {

    @DisplayName("유저 이름 생성에 성공한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "김재연",
            "matt",
            "kjy",
            "ggg"
    })
    void Create(final String rawUsername) {
        final Username username = Username.of(rawUsername);
    }

    @DisplayName("유저 이름에 빈 값이나 null 값이 들어가면 실패한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void Create_UsernameEmptyOrNull_ThrowException(final String rawUsername) {
        Assertions.assertThatExceptionOfType(EmptyUsernameException.class)
                .isThrownBy(() -> {
                    Username.of(rawUsername);
                });
    }

    @DisplayName("유저 이름에 길이가 " + Username.MAX_LENGTH + " 를 넘는 값이 들어가면 실패한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "김재연123",
            "mattsdasda",
            "kjysgadasadgads",
            "gggdsa 아ㅏㅏㅏㅏㅏ sadsas "
    })
    void Create_LongerThanMaxLengthUsername_ThrowException(final String longerThanMaxLengthUsername) {
        Assertions.assertThatExceptionOfType(LongerThanMaxLengthUsernameException.class)
                .isThrownBy(() -> {
                    Username.of(longerThanMaxLengthUsername);
                });
    }

    @DisplayName("유저 이름이 같다면 같은 객체여야 한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "김재연",
            "matt",
            "kjy",
            "ggg"
    })
    void Equals_SameUsername_EqualsObject(final String rawUsername) {
        // given
        final Username username = Username.of(rawUsername);
        final Username expect = Username.of(rawUsername);

        // when
        final boolean equals = username.equals(expect);

        // then
        assertThat(equals).isTrue();
        assertThat(username == expect).isTrue();
    }
}