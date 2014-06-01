package ica.SCS;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View.OnClickListener;
import android.view.View;
import android.util.Log;
import ica.SCS.Core.*;

import java.util.*;

public class MainActivity extends Activity {
    private List<Game> games;
    private ListView gameList;
    private Activity me;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        me = this;
        super.onCreate(savedInstanceState);

        try {
            Scs.initialize(getApplicationContext());
            games = Scs.getGames();

            setContentView(R.layout.main);

            gameList = (ListView)findViewById(R.id.listGames);
        }
        catch (Exception ex){
            Log.e("onCreate", "Failed to load games?", ex);
        }
    }

    @Override
    public void onResume() {
        super.onResume ();

        gameList.setAdapter(new ListItemAdapter(getApplicationContext(), games));
        gameList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Game game = games.get(position);
                // display the game view
                Intent gameDetail = new Intent (me, GameActivity.class);
                gameDetail.putExtra("Game", game.getId());

                startActivity (gameDetail);
            }
        });
    }
}
