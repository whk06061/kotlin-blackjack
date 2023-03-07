package blackjack.domain.card

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CardDeckTest {

    @Test
    fun `블랙잭 게임은 초기에 카드 52장을 가진다`() {
        // given
        val cardDeck = CardDeck(RandomCardsGenerator())

        // when
        val actual = cardDeck.cards.size

        // then
        assertThat(actual).isEqualTo(52)
    }

    @Test
    fun `카드 발행이 가능한지 확인한다`() {
        // given
        val cardDeck = CardDeck(RandomCardsGenerator())

        // when
        val actual = cardDeck.checkProvidePossible()

        // then
        assertThat(actual).isTrue
    }

    @Test
    fun `카드를 한 장 발행하고 해당 카드를 삭제한다`() {
        // given
        val cardDeck = CardDeck(RandomCardsGenerator())

        // when
        cardDeck.provide()
        val actual = cardDeck.cards.size

        // then
        assertThat(actual).isEqualTo(51)
    }
}