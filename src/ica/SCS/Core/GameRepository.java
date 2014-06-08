package ica.SCS.Core;

import android.util.JsonReader;
import android.util.JsonToken;
import java.io.*;
import java.util.*;

/**
 * Created by jcapuano on 5/29/2014.
 */
public class GameRepository {

    public static ArrayList<Game> read(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readGames(reader);
        }
        finally {
            reader.close();
        }
    }
    private static ArrayList<Game> readGames(JsonReader reader) throws IOException {
        ArrayList<Game> games = new ArrayList<Game>();

        reader.beginArray();
        while (reader.hasNext()) {
            games.add(readGame(reader));
        }
        reader.endArray();
        return games;
    }

    private static Game readGame(JsonReader reader) throws IOException {
        Game game = new Game();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                game.setId(reader.nextInt());
            } 
            else if (name.equals("name")) {
                game.setName(reader.nextString());
            }                
            else if (name.equals("desc")) {
                game.setDesc(reader.nextString());
            }                
            else if (name.equals("image")) {
                game.setImage(reader.nextString());
            }                
            else if (name.equals("sort")) {
                game.setSort(reader.nextInt());
            } 
            else if (name.equals("custom")) {
                game.setCustom(reader.nextString());
            }                
            else if (name.equals("players") && reader.peek() != JsonToken.NULL) {
                game.setPlayers(readStrings(reader));
            } 
            else if (name.equals("turns") && reader.peek() != JsonToken.NULL) {
                game.setTurns(readStrings(reader));
            } 
            else if (name.equals("phases") && reader.peek() != JsonToken.NULL) {
                game.setPhases(readStrings(reader));
            } 
            else if (name.equals("terrains") && reader.peek() != JsonToken.NULL) {
                game.setTerrains(readTerrains(reader));
            } 
            else if (name.equals("combatTable") && reader.peek() != JsonToken.NULL) {
                game.getCombat().setTable(readCombatTable(reader));
            } 
            else if (name.equals("barrageTable") && reader.peek() != JsonToken.NULL) {
                game.getBarrage().setTable(readBarrageTable(reader));
            } 
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return game;
    }
    
    private static ArrayList<String> readStrings(JsonReader reader) throws IOException {
        ArrayList<String> l = new ArrayList<String>();

        reader.beginArray();
        while (reader.hasNext()) {
            l.add(reader.nextString());
        }
        reader.endArray();
        return l;
    }
        
    private static ArrayList<Terrain> readTerrains(JsonReader reader) throws IOException {
        ArrayList<Terrain> l = new ArrayList<Terrain>();
        
        reader.beginArray();
        while (reader.hasNext()) {
            Terrain t = new Terrain();
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("name")) {
                    t.setName(reader.nextString());
                }
                else if (name.equals("barrage")) {
                    t.setBarrage(readTerrainMod(reader));
                }
                else if (name.equals("combat")) {
                    t.setCombat(readTerrainMod(reader));
                }
                else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            l.add(t);
        }            
     
        reader.endArray();
        return l;
    }
    private static TerrainModifier readTerrainMod(JsonReader reader) throws IOException {
        TerrainModifier tm = new TerrainModifier();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("attackmod")) {
                Modifier m = readModifier(reader);
                m.setCount(1);
                tm.setAttack(m);
            }
            else if (name.equals("defendmod")) {
                Modifier m = readModifier(reader);
                m.setCount(1);
                tm.setDefend(m);
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return tm;
    }
    
    private static GameTable<Integer> readCombatTable(JsonReader reader) throws IOException {
        GameTable<Integer> t = new GameTable<Integer>();
        
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("dice")) {
                t.setDiceDefinition(readTableDiceDefinition(reader));
            }
            else if (name.equals("table")) {
                t.setTable(readTableInteger(reader));
            }
            else if (name.equals("modifiers")) {
                t.setModifiers(readModifiers(reader));
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        
        return t;
    }
    private static ArrayList<Results<Integer>> readTableInteger(JsonReader reader) throws IOException {
        ArrayList<Results<Integer>> l = new ArrayList<Results<Integer>>();
        
        reader.beginArray();
        while (reader.hasNext()) {
            Results<Integer> r = new Results<Integer>();
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("odds")) {
                    r.setValue(reader.nextInt());
                }
                else if (name.equals("results")) {
                    r.setResults(readResults(reader));
                }
                else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            l.add(r);
        }
        reader.endArray();
        return l;
    }

    
    private static GameTable<String> readBarrageTable(JsonReader reader) throws IOException {
        GameTable<String> t = new GameTable<String>();
        
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("dice")) {
                t.setDiceDefinition(readTableDiceDefinition(reader));
            }
            else if (name.equals("table")) {
                t.setTable(readTableString(reader));
            }
            else if (name.equals("modifiers")) {
                t.setModifiers(readModifiers(reader));
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        
        return t;
    }
    private static ArrayList<Results<String>> readTableString(JsonReader reader) throws IOException {
        ArrayList<Results<String>> l = new ArrayList<Results<String>>();
        
        reader.beginArray();
        while (reader.hasNext()) {
            Results<String> r = new Results<String>();
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("strength")) {
                    r.setValue(reader.nextString());
                }
                else if (name.equals("results")) {
                    r.setResults(readResults(reader));
                }
                else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            l.add(r);
        }
        reader.endArray();
        return l;
    }
    
    private static DiceDefinition readTableDiceDefinition(JsonReader reader) throws IOException {
        DiceDefinition dd = new DiceDefinition();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("number")) {
                dd.setNumber(reader.nextInt());
            }
            else if (name.equals("base")) {
                dd.setBase(reader.nextString());
            }
            else if (name.equals("sides")) {
                dd.setSides(reader.nextInt());
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return dd;
    }
    
    private static ArrayList<Result> readResults(JsonReader reader) throws IOException {
        ArrayList<Result> l = new ArrayList<Result>();
        reader.beginArray();
        while (reader.hasNext()) {
            Result r = new Result();
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("lo")) {
                    r.setLo(reader.nextInt());
                }
                else if (name.equals("hi")) {
                    r.setHi(reader.nextInt());
                }
                else if (name.equals("result")) {
                    r.setResult(reader.nextString());
                }
                else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            l.add(r);
        }
        reader.endArray();
        return l;
    }
    
    private static ArrayList<Modifier> readModifiers(JsonReader reader) throws IOException {
        ArrayList<Modifier> l = new ArrayList<Modifier>();
        reader.beginArray();
        while (reader.hasNext()) {
            l.add(readModifier(reader));
        }
        reader.endArray();
        return l;
    }
    
    private static Modifier readModifier(JsonReader reader) throws IOException {
        Modifier m = new Modifier();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("type")) {
                m.setType(reader.nextString());
            }
            else if (name.equals("name")) {
                m.setName(reader.nextString());
            }
            else if (name.equals("value")) {
                m.setValue(reader.nextDouble());
            }
            else if (name.equals("count")) {
                m.setCount(reader.nextInt());
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return m;
    }
    
}
