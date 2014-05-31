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

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public boolean lessThan(T v) {
        return (value.compareTo(v) > 0);
    }

    public Result getResult(int d) {
        for (Result r : this.results) {
            if (r.inRange(d))
                return r;
        }
        return null;
    }

}
