package ica.SCS.Core;

/**
 * Created by jcapuano on 5/30/2014.
 */
public class Saved {
    private int game;
    private int turn;
    private int phase;
    private int player1VP;
    private int player2VP;

    public Saved() {
        game = 0;
        turn = 0;
        phase = 0;
        player1VP = 0;
        player2VP = 0;
    }

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }
    
    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public int getPlayer1VP() {
        return player1VP;
    }

    public void setPlayer1VP(int player1VP) {
        this.player1VP = player1VP;
    }

    public int getPlayer2VP() {
        return player2VP;
    }

    public void setPlayer2VP(int player2VP) {
        this.player2VP = player2VP;
    }
    
    public void reset(Game g) {
		game = g.getId();
		turn = 0;
		phase = 0;
        player1VP = 0;
        player2VP = 0;
	}    
}
