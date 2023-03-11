package blackjack.domain.player

import blackjack.domain.Result
import blackjack.domain.card.Card
import blackjack.domain.card.CardNumber
import blackjack.domain.card.CardShape
import blackjack.domain.card.Cards
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ParticipantTest {

    @Test
    fun `참가자가 카드를 8클로버만 가지고 있을 때, 카드를 더 받을 수 있는지 확인하면, true이다`() {

        // given
        val participant = Participant("aaa", Cards(listOf(Card(CardNumber.EIGHT, CardShape.CLOVER))))

        // when
        val actual: Boolean = participant.checkProvideCardPossible()

        // then
        assertThat(actual).isTrue
    }

    @Test
    fun `참가자1 스코어는 Burst 딜러 스코어는 17일때, 참가자1의 승패를 계산하면, 패배이다`() {

        // given
        val dealer = Dealer(
            cards = Cards(
                listOf(
                    Card(CardNumber.EIGHT, CardShape.DIAMOND),
                    Card(CardNumber.NINE, CardShape.DIAMOND)
                )
            )
        )

        val participant1 = Participant(
            "aaa",
            cards = Cards(
                listOf(
                    Card(CardNumber.EIGHT, CardShape.DIAMOND),
                    Card(CardNumber.SEVEN, CardShape.HEART),
                    Card(CardNumber.NINE, CardShape.DIAMOND)
                )
            )
        )

        // when
        val actual: ParticipantResult = participant1.calculateResult(dealer)

        // then
        assertThat(actual).isEqualTo(ParticipantResult("aaa", Result.LOSE))
    }

    @Test
    fun `참가자1 스코어는 17 딜러 스코어는 Burst 일때, 참가자1의 승패를 계산하면, 승리이다`() {

        // given
        val dealer = Dealer(
            cards = Cards(
                listOf(
                    Card(CardNumber.QUEEN, CardShape.DIAMOND),
                    Card(CardNumber.KING, CardShape.DIAMOND),
                    Card(CardNumber.THREE, CardShape.SPADE)
                )
            )
        )
        val participant1 = Participant(
            "aaa",
            cards = Cards(
                listOf(
                    Card(CardNumber.EIGHT, CardShape.DIAMOND),
                    Card(CardNumber.NINE, CardShape.DIAMOND)
                )
            )
        )

        // when
        val actual: ParticipantResult = participant1.calculateResult(dealer)

        // then
        assertThat(actual).isEqualTo(ParticipantResult("aaa", Result.WIN))
    }
}
