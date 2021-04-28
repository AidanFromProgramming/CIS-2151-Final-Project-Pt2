package cards.dealer;

import cards.Card;
import cards.Hand;

import java.util.Objects;

public class Dealer extends Hand {
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int iteration = 0;

        for (Card card : cards) {
            if (iteration != 0) {
                assert card != null;
                stringBuilder.append(Objects.requireNonNull(card).getFace().toString());
                stringBuilder.append(" of ");
                stringBuilder.append(card.getSuit().toString());
                stringBuilder.append("\n");
            } else {
                stringBuilder.append("[BACK OF CARD]\n");
            }
            iteration++;
        }

        return stringBuilder.toString();
    }
}
