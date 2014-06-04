package ica.SCS.Core;

import java.util.*;

/**
 * Created by jcapuano on 5/25/2014.
 */
public class Game {
    private int id;
    private String name;
    private String desc;
    private String image;
    private int sort;
    private ArrayList<String> players;
    private ArrayList<String> turns;
    private ArrayList<String> phases;
    private ArrayList<Terrain> terrains;
    private Combat combat;
    private Barrage barrage;

    public Game() {
        combat = new Combat();
        barrage = new Barrage();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    public ArrayList<String> getTurns() {
        return turns;
    }

    public void setTurns(ArrayList<String> turns) {
        this.turns = turns;
    }

    public ArrayList<String> getPhases() {
        return phases;
    }

    public void setPhases(ArrayList<String> phases) {
        this.phases = phases;
    }

    public ArrayList<Terrain> getTerrains() {
        return terrains;
    }

    public void setTerrains(ArrayList<Terrain> terrains) {
        this.terrains = terrains;
    }

    public Combat getCombat() {
        return combat;
    }

    public void setCombat(Combat combat) {
        this.combat = combat;
    }

    public Barrage getBarrage() {
        return barrage;
    }

    public void setBarrage(Barrage barrage) {
        this.barrage = barrage;
    }
    
    public Terrain getDefaultTerrain() {
        return terrains.get(0);
    }
    
    public String[] getTerrainList() {
		ArrayList<String> l = new ArrayList<String>();
		for (Terrain t : terrains)
			l.add(t.getName());
        String[] a = new String[l.size()];
		l.toArray(a);
        return a;
	}    
    
    public int getTerrainIndex(Terrain t) {
		for (int i=0; i<terrains.size(); i++) {
			if (terrains.get(i).getName().equals(t.getName()))
				return i;
		}
		return 0;
	}    
    
}
