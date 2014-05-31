package ica.SCS.Core;

import android.content.Context;
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
    
    public static List<Game> getGames() throws IOException {
        if (games == null) {
            games = GameRepository.read(ctx.getAssets().open("games.js"));
        }
        return games;
    }
    
    public static Game getGame(int id) throws IOException {
        List<Game> l = getGames();
        for (Game g : l) {
			if (g.getId() == id)
				return g;
		}
		return null;        
    }
    
    public static Saved getSaved() throws FileNotFoundException, IOException {
        if (saved == null) {
            saved = SavedRepository.read(ctx.openFileInput("saved.js"));
        }
        return saved;
    }
    
    public static void saveSaved() throws FileNotFoundException, IOException {
        if (saved != null) {
            SavedRepository.write(ctx.openFileOutput("saved.js", 0), saved);
        }
    }
}
