package com.jaeyeonling.ladder.view.format;

import com.jaeyeonling.ladder.User;
import com.jaeyeonling.ladder.Username;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class UserFormatterTest {

    private static final Formatter<Username> usernameFormatter = new UsernameFormatter();
    private static final Formatter<User> userFormatter = UserFormatter.of(usernameFormatter);

    @DisplayName("유저 포매터 생성에 성공한다.")
    @Test
    void create() {
        // then
        assertThat(userFormatter).isNotNull();
    }

    @DisplayName("포맷팅 시 길이는 " + Username.MAX_LENGTH + " 이어야 한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "김재연",
            "matt",
            "kjy",
            "ggg"
    })
    void Format_User_LengthShouldSame(final String rawUsername) {
        // given
        final User user = User.of(rawUsername);

        // when
        final String formattedUser = userFormatter.format(user);

        // then
        assertThat(formattedUser).hasSize(Username.MAX_LENGTH);
    }

    @DisplayName("포맷팅 후 trim 시 입력과 같은 값이어야 한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "김재연",
            "matt",
            "kjy",
            "ggg",
            "다섯글자다"
    })
    void FormatAndTrim_User_ShouldSameValue(final String rawUsername) {
        // given
        final User user = User.of(rawUsername);

        // when
        final String formattedUsername = userFormatter.format(user).trim();

        // then
        assertThat(formattedUsername).isEqualTo(rawUsername);
    }
}
