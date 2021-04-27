package player;

import cards.Hand;
import exceptions.player.NotEnoughMoneyException;
import game.GameState;

public class Player {
    //Fields
    public final String name;
    public final Hand hand;

    public int money;
    public boolean bankrupt;

    //Constructor
    public Player(String name) {
        this.name = name;

        this.hand = new Hand();
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
}
