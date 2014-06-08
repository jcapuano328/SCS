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
    
    public ArrayList<CombatModifier> getModifiers() {
        ArrayList<CombatModifier> l = new ArrayList<CombatModifier>();
        ArrayList<Modifier> mods = table.getModifiers();
        for (Modifier m : mods) {
            l.add(new CombatModifier(m));
        }
        return l;
    }

    public Results<Integer> getOdds(int o) {
        return table.findResults(o, 0);
    }
    
    public Results<Integer> getOddsByIndex(int idx) {
        return table.getTable().get(idx);
    }
    
    public Results<Integer> getDefaultOdds() {
        return table.getDefault();
    }
    
    public String[] getOddsList() {
        return table.getValueList();
	}    
    
    public int getOddsIndex(Results<Integer> item) {
        return table.getValueIndex(item);
	}    
    
    public int calculate(double att, double def, ArrayList<Modifier> attmods, ArrayList<Modifier> defmods, Terrain terrain, Terrain terraintween) {
    
        int odds = 1;
        
        att = att * Modifier.modifierMULT(attmods) * terrain.getCombat().getAttack().modifyMULT() * terraintween.getCombat().getAttack().modifyMULT();
        def = def * Modifier.modifierMULT(defmods) * terrain.getCombat().getDefend().modifyMULT() * terraintween.getCombat().getDefend().modifyMULT();
                    
		if (att > 0 && def > 0) {
            odds = (int)Math.floor(((att >= def) ? (att/def) : (def/att)) + 0.5) * ((att < def) ? -1 : 1);
        }
        return odds;
    }

    public String resolve(int die1, int die2, int odds, ArrayList<Modifier> attmods, ArrayList<Modifier> defmods, Terrain terrain, Terrain terraintween) {
        
        int drm = Modifier.modifierDRM(attmods) - Modifier.modifierDRM(defmods);
        int dice = table.getDiceValue(die1, die2, drm);
        
        int shift = Modifier.modifierSHIFT(attmods) - Modifier.modifierSHIFT(defmods);
		
        Results<Integer> results = table.findResults(odds, shift);
        Result result = results.getResult(dice);
        return result.getResult();
	}
}
