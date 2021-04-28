package game;

import cards.Deck;
import exceptions.player.NotEnoughMoneyException;
import player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameState extends Thread implements Serializable {
    //Fields
    public int potValue;

    private int turn;
    private List<Player> players;
    private Deck deck;
    private boolean running;
    private int initialBet;

    //Constructor
    public GameState(String... playerNames) {
        //Setting up the deck
        deck = new Deck(6);
        deck.genAndShuffle();

        //Setting up players
        players = new ArrayList<>();
        int numberOfPlayers = playerNames.length;
        int iteration = 0;
        for (int player = 0; player < numberOfPlayers; player++) {
            players.add(new Player(playerNames[iteration]));
            iteration++;
        }

        //Start the Game
        startNewRound();
    }

    public void hitPressed(int playerNumber) {
        Player player = players.get(playerNumber);
        if (turn > 0 && turn % players.size() == playerNumber + 1) {
            player.hand.drawCard(deck);
            if (player.hand.calculateHandValue() > 21) {
                player.busted = true;
            }
            advanceTurn();
        }
    }

    public void standPressed(int playerNumber) {
        Player player = players.get(playerNumber);
        if (turn > 0 && turn % players.size() == playerNumber + 1) {
            player.standing = true;
            advanceTurn();
        }
    }

    public void doubleUpPressed(int playerNumber) {
        Player player = players.get(playerNumber);

        //Setting the player to be double up
        if (turn == 0) {
            //Forcing them to draw a card and checking if they busted
            players.get(playerNumber).hand.drawCard(deck);
            if (players.get(playerNumber).hand.calculateHandValue() > 21) {
                players.get(playerNumber).busted = true;
            }

            players.get(playerNumber).doubleUp = 1;
            initialBet += initialBet;
        }

        checkForLastDoubleUp();
    }

    public void dontDoubleUpPressed(int playerNumber) {
        //Setting the player to be a not double up
        if (turn == 0) {
            players.get(playerNumber).doubleUp = -1;
        }

        checkForLastDoubleUp();
    }

    //Getters and setters
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public boolean isRunning() {
        return running;
    }

    private void startNewRound() {
        if (deck.getCards().size() <= 30) {
            deck.genAndShuffle();
        }

        turn = 0;
        potValue = 0;
        initialBet = 1;

        for (Player player : players) {
            player.hand.drawCard(deck);
            player.hand.drawCard(deck);
        }
    }

    private void checkForLastDoubleUp() {
        //Checking if this is the last double up
        boolean allPlayersSelected = true;
        for (Player player : players) {
            if (player.bankrupt) continue;
            if (player.doubleUp == 0) {
                allPlayersSelected = false;
            }
        }

        //If it is, make all players pay the initial bet according the the double up values
        if (allPlayersSelected) {
            for (Player player : players) {
                if (player.bankrupt) continue;
                try {
                    player.bet(this, initialBet);
                } catch (NotEnoughMoneyException e) {
                    player.bankrupt = true;
                }
            }

            advanceTurn();
        }
    }

    private void advanceTurn() {
        boolean validPlayer = false;
        int player = 1;
        while (!validPlayer) {
            if (!players.get(player - 1).busted && !players.get(player - 1).standing) {
                validPlayer = true;
            } else {
                player++;
            }
            if (player > players.size()) {
                endRound();
                return;
            }
        }
        turn = player;
    }

    private void endRound() {
        //Divvying up money to highest scoring individuals (very inefficient I know)
        List<Integer> playerHandValues = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            playerHandValues.set(i, players.get(i).hand.calculateHandValue());
        }
        int highestHand = 0;
        for (int i = 0; i < players.size(); i++) {
            if (playerHandValues.get(i) > highestHand) {
                highestHand = playerHandValues.get(i);
            }
        }
        List<Integer> playersWithHighestHand = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).hand.calculateHandValue() == highestHand) {
                playersWithHighestHand.add(i);
            }
        }
        for (int i : playersWithHighestHand) {
            players.get(i).money += potValue / playersWithHighestHand.size();
        }

        //Start new round
        startNewRound();
    }
}
