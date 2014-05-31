package ica.SCS.Core;

import java.util.*;

/**
 * Created by jcapuano on 5/26/2014.
 */
public class GameTable<T extends Comparable<? super T>> {
    private DiceDefinition dd;
    private ArrayList<Results<T>> table;
    private ArrayList<Modifier> modifiers;

    public GameTable() {
        table = new ArrayList<Results<T>>();
        modifiers = new ArrayList<Modifier>();
    }

    public void setDiceDefinition(DiceDefinition dd) {
        this.dd = dd;
    }

    public int getNumDice() {
        return dd.getNumber();
    }

    public void setNumDice(int numDice) {
        dd.setNumber(numDice);
    }

    public String getBaseDice() {
        return dd.getBase();
    }

    public void setBaseDice(String baseDice) {
        dd.setBase(baseDice);
    }

    public int getSidesDice() {
        return dd.getSides();
    }

    public void setSidesDice(int sidesDice) {
        dd.setSides(sidesDice);
    }

    public ArrayList<Results<T>> getTable() {
        return table;
    }

    public void setTable(ArrayList<Results<T>> table) {
        this.table = table;
    }

    public ArrayList<Modifier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(ArrayList<Modifier> modifiers) {
        this.modifiers = modifiers;
    }

    public int getDiceValue(int die1, int die2, int drm) {
	    int dice = 0;
	    if (dd.getBase().equals("d")) {
	        int max = dd.getNumber() > 1 ? 14 : 8;
	        dice = die1 + (dd.getNumber() > 1 ? die2 : 0) + drm;
			if (dice < 0) {dice = 0;}
	        if (dice > max) {dice = max;}
	    }
	    else if (dd.getBase().equals("b")) {
	        dice = die1*10 + die2 + drm;
	        if (dice < 11) {dice = 11;}                    
			else if (dice > 66) {dice = 66;}
	    }
        else {
            dice = die1 + die2 + drm;
        }
	    
	    return dice;
	}

    public Results<T> findResults(T v, int shift) {
        Results<T> res = null;
        int idx = 0;
        for (int i=0; i<table.size(); i++) {
            Results<T> r = table.get(i);
            if (r.lessThan(v)) {
                idx = i;
                break;
            }
            
        }
		if (idx < 0) {
            idx = table.size() - 1;
		}                
		idx += shift;
		if (idx < 0) {
            idx = 0;
		}                
		else if (idx >= table.size()) {
            idx = table.size() - 1;
		}                
		
		return table.get(idx);
	}
    
}
