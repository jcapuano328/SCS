package ica.SCS.Core;

/**
 * Created by jcapuano on 5/26/2014.
 */
public class Terrain {
    private String name;
    private TerrainModifier barrage;
    private TerrainModifier combat;

    public Terrain() {
        this.name = "";
        barrage = new TerrainModifier();
        combat = new TerrainModifier();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TerrainModifier getBarrage() {
        return barrage;
    }

    public void setBarrage(TerrainModifier barrage) {
        this.barrage = barrage;
    }

    public TerrainModifier getCombat() {
        return combat;
    }

    public void setCombat(TerrainModifier combat) {
        this.combat = combat;
    }
}

