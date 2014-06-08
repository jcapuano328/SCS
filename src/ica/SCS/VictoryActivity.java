package ica.SCS;

import android.app.Activity;
import android.content.*;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import ica.SCS.Core.*;
import java.util.*;

/**
 * Created by jcapuano on 6/7/2014.
 */
public class VictoryActivity extends Activity {
	private ImageView imgBack;
	private ImageView imgScs;
	private TextView txtGameName;
	private TextView txtGameDesc;
    
	private TextView txtPlayer1;
	private Button btnPlayer1VPPrev;
	private Button btnPlayer1VPNext;
	private EditText editPlayer1VP;
	
	private TextView txtPlayer2;
	private Button btnPlayer2VPPrev;
	private Button btnPlayer2VPNext;
	private EditText editPlayer2VP;
    
    private Game game;
    private Saved saved;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        Intent intent = getIntent();
        game = Scs.getGame(intent.getIntExtra ("Game", -1));
        saved = Scs.getSaved(game);
        
        setContentView(R.layout.victory);
        
		imgBack = (ImageView)findViewById(R.id.titleSubScsBack);
		imgScs = (ImageView)findViewById(R.id.titleSubScs);
		txtGameName = (TextView)findViewById(R.id.titleSubGameName);
		txtGameDesc = (TextView)findViewById(R.id.titleSubGameDesc);
        
		// attacker
		txtPlayer1 = (TextView)findViewById(R.id.txtPlayer1);
		btnPlayer1VPPrev = (Button)findViewById(R.id.btnPlayer1VPPrev);
		btnPlayer1VPNext = (Button)findViewById(R.id.btnPlayer1VPNext);
		editPlayer1VP = (EditText)findViewById(R.id.editPlayer1VP);
		editPlayer1VP.setText("0");
		
		// defender
		txtPlayer2 = (TextView)findViewById(R.id.txtPlayer2);
		btnPlayer2VPPrev = (Button)findViewById(R.id.btnPlayer2VPPrev);
		btnPlayer2VPNext = (Button)findViewById(R.id.btnPlayer2VPNext);
		editPlayer2VP = (EditText)findViewById(R.id.editPlayer2VP);
		editPlayer2VP.setText("0");
    }
    
    @Override
    public void onResume () {
        super.onResume();

        int resid = getApplicationContext().getResources().getIdentifier("drawable/" + game.getImage() + "sm", null, getApplicationContext().getPackageName());
        imgScs.setImageResource(resid);

        imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    navigateUp();
			}
		});        
        
		imgScs.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    navigateUp();
			}
		});        
        
		txtGameName.setText(game.getName());
		txtGameDesc.setText(game.getDesc());
    
        ArrayList<String> players = game.getPlayers();
        txtPlayer1.setText(players.size() > 0 ? players.get(0) : "");
        txtPlayer2.setText(players.size() > 1 ? players.get(1) : "");
        
		// attacker
		btnPlayer1VPPrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    int value = getPlayer1VP();
			    if (--value < 1) value = 1;
                saved.setPlayer1VP(value);
			    editPlayer1VP.setText(Integer.toString(value));
                save();
			}
		});
		btnPlayer1VPNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    int value = getPlayer1VP() + 1;
                saved.setPlayer1VP(value);
			    editPlayer1VP.setText(Integer.toString(value));
                save();
			}
		});
		editPlayer1VP.addTextChangedListener(new TextWatcher() {
			@Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
			@Override
            public void onTextChanged(CharSequence s, int start, int before, int end) {
            }
			@Override
            public void afterTextChanged(Editable s) {
                saved.setPlayer1VP(getPlayer1VP());
                save();
            }
        });
        
		// defender
		btnPlayer2VPPrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    int value = getPlayer2VP();
			    if (--value < 1) value = 1;
                saved.setPlayer2VP(value);
			    editPlayer2VP.setText(Integer.toString(value));
                save();
			}
		});
		btnPlayer2VPNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    int value = getPlayer2VP() + 1;
                saved.setPlayer2VP(value);
			    editPlayer2VP.setText(Integer.toString(value));
                save();
			}
		});
		editPlayer2VP.addTextChangedListener(new TextWatcher() {
			@Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
			@Override
            public void onTextChanged(CharSequence s, int start, int before, int end) {
            }
            public void afterTextChanged(Editable s) {
                saved.setPlayer2VP(getPlayer2VP());
                save();
            }
        });
        
        editPlayer1VP.setText(Integer.toString(saved.getPlayer1VP()));
        editPlayer2VP.setText(Integer.toString(saved.getPlayer2VP()));
	}
 
	int getPlayer1VP() {
        String v = editPlayer1VP.getText().toString();
        if (!v.isEmpty())
            return Integer.parseInt(v);
        return 1;           
	}
	int getPlayer2VP() {
        String v = editPlayer2VP.getText().toString();
        if (!v.isEmpty())
            return Integer.parseInt(v);
        return 1;           
	}
    
    private void save() {
        Scs.saveSaved();
	}
    
    private void navigateUp() {
		Intent gameDetail = new Intent (this, GameActivity.class);
		gameDetail.putExtra("Game", game.getId());
		startActivity (gameDetail);
	}
}