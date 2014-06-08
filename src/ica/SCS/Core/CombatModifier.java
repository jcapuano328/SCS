package ica.SCS.Core;

/**
 * Created by jcapuano on 5/26/2014.
 */
public class CombatModifier extends Modifier {
    private int defcount;
    public CombatModifier() {
        super();
        defcount = 0;
    }
    public CombatModifier(Modifier m) {
        super(m);
    }
    public CombatModifier(CombatModifier m) {
        super(m);
        defcount = m.getDefenderCount();
    }

    public int getDefenderCount() {
        return defcount;
    }
    public void setDefenderCount(int count) {
        this.defcount = count;
    }
}
