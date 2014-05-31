package ica.SCS.Core;

import java.util.*;

/**
 * Created by jcapuano on 5/26/2014.
 */
public class Barrage {
    private GameTable<String> table;

    public Barrage() {
        this.table = new GameTable<String>();
    }

    public GameTable<String> getTable() {
        return table;
    }

    public void setTable(GameTable<String> table) {
        this.table = table;
    }

    public String resolve(int die1, int die2, String str, Terrain terrain, ArrayList<Modifier> modifiers) {
        int dice = table.getDiceValue(die1, die2, 0);
        int drm = 0;
        int shift = 0;
		if (terrain != null) {
			shift = terrain.getBarrage().getAttack().modifySHIFT() + 
                    terrain.getBarrage().getDefend().modifySHIFT();
        
			dice += terrain.getBarrage().getAttack().modifyDRM() + 
                    terrain.getBarrage().getDefend().modifyDRM();
		}
		
        shift += Modifier.modifierSHIFT(modifiers);
        dice += Modifier.modifierDRM(modifiers);

        Results<String> results = table.findResults(str, shift);
        Result result = results.getResult(dice);
        return result.getResult();
	}
}
