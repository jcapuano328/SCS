package ica.SCS.Core;

import android.util.JsonReader;
import android.util.JsonWriter;
import java.io.*;
/**
 * Created by jcapuano on 5/31/2014.
 */
public class SavedRepository {

    public static Saved read(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readSaved(reader);
        }
        finally {
            reader.close();
        }
    }
    private static Saved readSaved(JsonReader reader) throws IOException {
        Saved saved = new Saved();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("game")) {
                saved.setGame(reader.nextInt());
            } 
            else if (name.equals("turn")) {
                saved.setTurn(reader.nextInt());
            } 
            else if (name.equals("phase")) {
                saved.setPhase(reader.nextInt());
            } 
            else if (name.equals("player1VP")) {
                saved.setPlayer1VP(reader.nextInt());
            } 
            else if (name.equals("player2VP")) {
                saved.setPlayer2VP(reader.nextInt());
            } 
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return saved;
    }

    public static void write(OutputStream out, Saved saved) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("  ");
        try {
            writeSaved(writer, saved);
        }
        finally {
            writer.close();
        }
    }
    private static void writeSaved(JsonWriter writer, Saved saved) throws IOException {
        writer.beginObject();
        writer.name("game").value(saved.getGame());
        writer.name("turn").value(saved.getTurn());
        writer.name("phase").value(saved.getPhase());
        writer.name("player1VP").value(saved.getPlayer1VP());
        writer.name("player2VP").value(saved.getPlayer2VP());
        writer.endObject();
    }    
    
}
