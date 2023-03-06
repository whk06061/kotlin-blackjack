package blackjack.controller

import blackjack.domain.card.CardDeck
import blackjack.domain.player.Dealer
import blackjack.domain.player.Participant
import blackjack.domain.player.Participants
import blackjack.domain.player.Player
import blackjack.view.InputView
import blackjack.view.OutputView

class BlackjackController(
    private val inputView: InputView = InputView(),
    private val outputView: OutputView = OutputView(),
    private val cardDeck: CardDeck = CardDeck()
) {

    fun run() {
        val dealer = Dealer()
        val participants = readParticipants()
        settingPlayersCards(dealer, participants)
        readParticipantsMoreCard(participants)
        giveDealerMoreCard(dealer)
        participants.values.forEach {
            it.updateResult(dealer.cards.sumCardsNumber())
        }
        dealer.updateResults(participants.values.map { it.cards.sumCardsNumber() })
        printSumResult(dealer, participants)
        printFinalResult(dealer, participants)
    }

    private fun readParticipants(): Participants =
        Participants(inputView.readParticipantsName().map { Participant(it) })

    private fun settingPlayersCards(dealer: Dealer, participants: Participants) {
        repeat(CARD_SETTING_COUNT) {
            provideCard(dealer)
        }
        participants.values.forEach { participant ->
            repeat(CARD_SETTING_COUNT) {
                provideCard(participant)
            }
        }

        outputView.printSettingCard(dealer, participants)
    }

    private fun provideCard(player: Player) {
        cardDeck.apply {
            if (checkProvidePossible()) player.addCard(provide())
            else outputView.printNoCardMessage()
        }
    }

    private fun readParticipantsMoreCard(participants: Participants) {
        participants.values.forEach {
            readParticipantMoreCard(it)
        }
    }

    private fun readParticipantMoreCard(participant: Participant) {
        while (true) {
            val check = participant.isGenerateCardPossible()
            if (check) {
                val answer = inputView.readMoreCard(participant.name)
                if (answer == ANSWER_MORE_CARD) {
                    provideCard(participant)
                }
                outputView.printParticipantCards(participant)
                if (answer == ANSWER_NOT_MORE_CARD) break
            }
            if (!check) break
        }
    }

    private fun giveDealerMoreCard(dealer: Dealer) {
        val check = dealer.checkMustGenerateCard()
        if (check) {
            provideCard(dealer)
            outputView.printDealerHitCardMent()
        }
        if (!check) outputView.printDealerNotHitCardMent()
    }

    private fun printSumResult(dealer: Dealer, participants: Participants) {
        outputView.printSumResult(dealer, participants)
    }

    private fun printFinalResult(dealer: Dealer, participants: Participants) {
        outputView.printFinalResult(dealer, participants)
    }

    companion object {
        private const val CARD_SETTING_COUNT = 2
        private const val ANSWER_MORE_CARD = "y"
        private const val ANSWER_NOT_MORE_CARD = "n"
    }
}
