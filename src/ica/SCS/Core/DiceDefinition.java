package ica.SCS.Core;

/**
 * Created by jcapuano on 5/30/2014.
 */
public class DiceDefinition {
    private int number;
    private int sides;
    private String base;

    public DiceDefinition() {
        number = 0;
        sides = 0;
        base = "";
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSides() {
        return sides;
    }

    public void setSides(int sides) {
        this.sides = sides;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }
}
