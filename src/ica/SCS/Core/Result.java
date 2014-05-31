package ica.SCS.Core;

/**
 * Created by jcapuano on 5/26/2014.
 */
public class Result {
    private int lo;
    private int hi;
    private String result;

    public int getLo() {
        return lo;
    }

    public void setLo(int lo) {
        this.lo = lo;
    }

    public int getHi() {
        return hi;
    }

    public void setHi(int hi) {
        this.hi = hi;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean inRange(int d) {
        return d < lo || (d >= lo && d <= hi);
    }
}

