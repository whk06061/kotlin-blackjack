package blackjack.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ResultTest {

    @ParameterizedTest
    @ValueSource(strings = ["승", "패", "무"])
    fun `승패무 결과를 가진다`(expected: String) {
        val resultWord: List<String> = Result.values().map { it.word }
        assertThat(resultWord).contains(expected)
    }

    @Test
    fun `결과를 받아 반대의 결과를 돌려준다`() {
        assertThat(Result.reverse(Result.LOSE)).isEqualTo(Result.WIN)
    }
}
