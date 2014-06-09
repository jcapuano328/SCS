package ica.SCS.Core;

import android.content.Context;
import android.util.Log;
import java.io.*;
import java.util.*;


/**
 * Created by jcapuano on 5/30/2014.
 */
public class Scs {
    private static Context ctx;
    private static List<Game> games;
    private static Saved saved;

    public static void initialize(Context c) {
        ctx = c;
    }
    
    public static List<Game> getGames() {
        try {
            if (games == null) {
                games = GameRepository.read(ctx.getAssets().open("games.js"));
            }
        }
        catch (Exception ex) {
            Log.e("getGames", "Failed to get games", ex);
        }
        return games;
    }
    
    public static Game getGame(int id) {
        List<Game> l = getGames();
        for (Game g : l) {
			if (g.getId() == id)
				return g;
		}
		return null;        
    }
    
    public static Saved getSaved(Game game) {
        try {
            if (saved == null) {
                saved = SavedRepository.read(ctx.openFileInput("saved.js"));
            }
        }
        catch (FileNotFoundException fex) {
            saved = new Saved();
            saved.setGame(game.getId());
        }
        catch (Exception ex) {
            Log.e("getSaved", "Failed to get saved game", ex);
        }
        return saved;
    }
    
    public static void saveSaved() {
        try {
            if (saved != null) {
                SavedRepository.write(ctx.openFileOutput("saved.js", 0), saved);
            }
        }
        catch (Exception ex) {
            Log.e("saveSaved", "Failed to save game", ex);
        }
    }
    
    public static void prevTurn(Game game, Saved saved) {
        int turn = saved.getTurn();
        if (--turn < 0) {
            turn = 0;
        }
        else if (turn >= game.getTurns().size()) {
            turn = game.getTurns().size() - 1;
        }
        saved.setTurn(turn);
    }
    
    public static void nextTurn(Game game, Saved saved) {
        int turn = saved.getTurn();
        if (turn < 0) {
            turn = 0;
        }
        else if (++turn >= game.getTurns().size()) {
            turn = game.getTurns().size() - 1;
        }
        saved.setTurn(turn);
    }

    public static String offsetTurn(Game game, Saved saved, int offset) {
        int turn = saved.getTurn() + offset;
        
        if (turn < 0) {
            turn = 0;
        }
        else if (++turn >= game.getTurns().size()) {
            turn = game.getTurns().size() - 1;
        }
        return game.getTurns().get(turn);
    }
    
    public static String getCurrentTurn(Game game, Saved saved) {
        return game.getTurns().get(saved.getTurn());
    }
    
    public static void prevPhase(Game game, Saved saved) {
        int phase = saved.getPhase();
		if (--phase < 0) {
            prevTurn(game, saved);
            phase = game.getPhases().size() - 1;
		}
		else if (phase >= game.getPhases().size()) {
            nextTurn(game, saved);
		    phase = 0;
		}
        saved.setPhase(phase);
    }   
    
    public static void nextPhase(Game game, Saved saved) {
        int phase = saved.getPhase();
		if (phase < 0) {
            prevTurn(game, saved);
            phase = game.getPhases().size() - 1;
		}
		else if (++phase >= game.getPhases().size()) {
            nextTurn(game, saved);
		    phase = 0;
		}
        saved.setPhase(phase);
    }
    
    public static String getCurrentPhase(Game game, Saved saved) {
        return game.getPhases().get(saved.getPhase());
    }
}
