package ica.SCS.Core;

import java.util.*;

/**
 * Created by jcapuano on 5/16/2014.
 */
public class Dice {
	private ArrayList<Die> dice = new ArrayList<Die>();

	public Dice(int numdice, int low, int high) {
		for (int i=0; i<numdice; i++)
			addDie(low, high);
	}

	public int getDie(int die) {
        Die d = getDieItem(die);
        if (d != null) 
            return d.getValue();
		return 0;
	}
	public void setDie(int die, int value) {
        Die d = getDieItem(die);
        if (d != null) 
            d.setValue(value);
	}
	public Die getDieItem(int die) {
		if (die >= 0 && die < dice.size())
			return dice.get(die); 
		return null;
	}
    
	public int getSize() {
        return dice.size();
	}

	public void addDie(int rangelow, int rangehigh)
	{
		dice.add(new Die(rangelow, rangehigh));
	}
	public void removeDie(int die)
	{
		if (die >= 0 && die < dice.size())
			dice.remove(die);
	}
    
	public void clear() {
		dice.clear();
	}
    
	public void roll() {
		for (Die die : dice)
			die.roll();
	}
}
