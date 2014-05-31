package ica.SCS.Core;

import java.util.Random;

/**
 * Created by jcapuano on 5/16/2014.
 */
public class Die {

	private int rangelow;
	private int rangehigh;
	private int value;
    private Random random = new Random();

	public Die(int rangelo, int rangehi)
	{
		rangelow = rangelo;
		rangehigh = rangehi;
		value = rangelow;
	}

	public int getRangeLow() {
        return rangelow;
	}
	public void setRangeLow(int i) {
        rangelow = i;
	}

	public int getRangeHigh() {
        return rangehigh;
	}
	public void setRangeHigh(int i) {
        rangehigh = i;
	}

	public int getValue() {
        return value;
	}
	public void setValue(int i) {
        value = i;
	}
	
	public int increment(boolean rollover)
	{
        if (++value > rangehigh) {
            value = rollover ? rangelow : rangehigh;
        }
		return value;
	}
	
	public int decrement(boolean rollover)
	{
        if (--value < rangelow) {
            value = rollover ? rangehigh : rangelow;
        }
		return value;
    }
	
	public int roll() {		
        //value = rangelow + (int)(Math.random() * ((rangehigh - rangelow) + 1));
        value = random.nextInt(rangehigh - rangelow) + rangelow;
		
		return value;
	}

}
