package ica.SCS.Core;

import java.util.*;
/**
 * Created by jcapuano on 5/26/2014.
 */
public class Combat {
    private GameTable<Integer> table;

    public Combat() {
        this.table = new GameTable<Integer>();
    }

    public GameTable<Integer> getTable() {
        return table;
    }

    public void setTable(GameTable<Integer> table) {
        this.table = table;
    }

    public Odds calculate(double att, double def, ArrayList<Modifier> attmods, ArrayList<Modifier> defmods, Terrain terrain, Terrain terraintween) {
    
        Odds odds = new Odds(1, "1:1");
        
        att = att * Modifier.modifierMULT(attmods) * terrain.getCombat().getAttack().getValue() * terraintween.getCombat().getAttack().getValue();
        def = def * Modifier.modifierMULT(defmods) * terrain.getCombat().getDefend().getValue() * terraintween.getCombat().getDefend().getValue();
                    
		if (att > 0 && def > 0) {
            odds.setOdds((int)Math.floor(((att >= def) ? (att/def) : (def/att)) + 0.5));
            odds.setDisplay((att>=def) ? (Integer.toString(odds.getOdds()) + "':1") : ("1:" + Integer.toString(odds.getOdds())));
            odds.setOdds(odds.getOdds() * ((att < def) ? -1 : 1));
        }
        return odds;
    }

    public String resolve(int die1, int die2, Odds odds, ArrayList<Modifier> attmods, ArrayList<Modifier> defmods, Terrain terrain, Terrain terraintween) {
        
        int drm = Modifier.modifierDRM(attmods) - Modifier.modifierDRM(defmods);
        int dice = table.getDiceValue(die1, die2, drm);
        
        int shift = Modifier.modifierSHIFT(attmods) - Modifier.modifierSHIFT(defmods);
		
        Results<Integer> results = table.findResults(odds.getOdds(), shift);
        Result result = results.getResult(dice);
        return result.getResult();
	}
}
