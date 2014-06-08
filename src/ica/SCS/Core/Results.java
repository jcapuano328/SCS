package ica.SCS.Core;

import java.util.*;

/**
 * Created by jcapuano on 5/26/2014.
 */
public class Results<T extends Comparable<? super T>> {
    private T value;
    private ArrayList<Result> results;

    public Results() {
        this.results = new ArrayList<Result>();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T v) {
        this.value = v;
    }

    public String getDisplayValue() {
        if (value.getClass() == Integer.class) {
            int i = Integer.parseInt(value.toString());
            int v = Math.abs(i);
            return (i < 0) ? ("1:" + Integer.toString(v)) : (Integer.toString(v) + ":1");
        }
        return value.toString();
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public boolean compare(T v) {
        if (v.getClass() == String.class)
            return value.equals(v);
        if (v.getClass() == Integer.class)
            return (value.compareTo(v) >= 0);
        return false;
    }

    public Result getResult(int d) {
        for (Result r : this.results) {
            if (r.inRange(d))
                return r;
        }
        return null;
    }

}
