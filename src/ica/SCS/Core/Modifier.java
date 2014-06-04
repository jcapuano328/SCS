package ica.SCS.Core;

import java.util.*;

/**
 * Created by jcapuano on 5/26/2014.
 */
public class Modifier {
    private String type;
    private String name;
    private double value;
    private int count;
    
    
    public Modifier() {
        type = "";
        name = "";
        value = 0;
        count = 0;
    }
    public Modifier(Modifier m) {
        type = m.getType();
        name = m.getName();
        value = m.getValue();
        count = m.getCount();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public double modifyMULT() {
        double mult = 1;
        if (type.equals("mult") && count > 0) {
            mult *= (value / count);
        }
	    return mult;
	}
    
    public int modifyDRM() {
	    int drm=0;
        if (type.equals("drm") && count > 0) {
            drm = (int)(value * count);
        }
	    return drm;
	}
    
    public int modifySHIFT() {
	    int shift=0;
        if (type.equals("shift") && count > 0) {
            shift = (int)(value * count);
        }
	    return shift;
	}
    
    public static double modifierMULT(ArrayList<Modifier> modifiers) {
        double mult = 1;
        for (Modifier m : modifiers) {
            mult *= m.modifyMULT();
        }
        return mult;
	}
    
    public static int modifierDRM(ArrayList<Modifier> modifiers) {
        int drm = 0;
        for (Modifier m : modifiers) {
            drm += m.modifyDRM();
        }
        return drm;
	}
    
    public static int modifierSHIFT(ArrayList<Modifier> modifiers) {
        int shift = 0;
        for (Modifier m : modifiers) {
            shift += m.modifySHIFT();
        }
        return shift;
	}
}
