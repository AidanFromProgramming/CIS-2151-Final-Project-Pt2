package player;

import cards.Card;
import cards.Hand;
import exceptions.player.NotEnoughMoneyException;
import game.GameState;

import java.io.Serializable;

public class Player implements Serializable {
    //Fields
    public final String name;
    public final Hand hand;

    public int money;
    public int doubleUp;
    public boolean bankrupt;
    public boolean busted;
    public boolean standing;

    //Constructor
    public Player(String name) {
        this.name = name;

        this.hand = new Hand();
        doubleUp = 0;
    }

    //Methods
    public void bet(GameState gameState, int amount) throws NotEnoughMoneyException {
        if (money - amount < 0) {
            throw new NotEnoughMoneyException("Tried to bet $" + amount + " but only had $" + money);
        } else {
            money -= amount;
            gameState.potValue += amount;
        }
    }

    public String getHandToString() {
        return "Test ♣";
    }

    public String toString() {
        return name;
    }
}
