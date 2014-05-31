package ica.SCS.Core;

/**
 * Created by jcapuano on 5/26/2014.
 */
public class TerrainModifier {
    private Modifier attack;
    private Modifier defend;
    
    public TerrainModifier() {
        attack = new Modifier();
        defend  = new Modifier();
    }

    public Modifier getAttack() {
        return attack;
    }

    public void setAttack(Modifier attack) {
        this.attack = attack;
    }

    public Modifier getDefend() {
        return defend;
    }

    public void setDefend(Modifier defend) {
        this.defend = defend;
    }
}
