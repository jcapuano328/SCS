package ica.SCS.Core;

/**
 * Created by jcapuano on 5/26/2014.
 */
public class Odds {
    private int odds;
    private String display;

    public Odds(int odds, String display) {
        this.odds = odds;
        this.display = display;
    }

    public int getOdds() {
        return odds;
    }

    public void setOdds(int odds) {
        this.odds = odds;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
