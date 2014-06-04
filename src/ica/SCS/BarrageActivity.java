package ica.SCS;

import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import ica.SCS.Core.*;
import ica.SCS.Helpers.*;
import java.util.*;

/**
 * Created by jcapuano on 6/1/2014.
 */
public class BarrageActivity extends Activity {
	private ImageView imgBack;
	private ImageView imgScs;
	private TextView txtGameName;
	private TextView txtGameDesc;
    
    private Spinner spinBarrageStrength;
    private Spinner spinBarrageTerrain;
    
    private ListView listModifiers;
    
	private ImageView imgBarrageDie1;
	private ImageView imgBarrageDie2;
	private Button btnBarrageDiceRoll;
	
	private TextView txtBarrageResults;
    
    private Game game;
    private Dice dice;
	private PlayAudio audio;
    private Results<String> strength;
    private Terrain terrain;
    private ArrayList<Modifier> modifiers;


    @Override
    public void onCreate(Bundle savedInstanceState) {
		dice = new Dice(2, 1, 6);
		audio = new PlayAudio (this);
        
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        game = Scs.getGame(intent.getIntExtra ("Game", -1));
        
        modifiers = game.getBarrage().getTable().cloneModifiers();
        strength = game.getBarrage().getDefaultStrength();
        terrain = game.getDefaultTerrain();
    
        setContentView(R.layout.barrage);
        
		imgBack = (ImageView)findViewById(R.id.titleSubScsBack);
		imgScs = (ImageView)findViewById(R.id.titleSubScs);
		txtGameName = (TextView)findViewById(R.id.titleSubGameName);
		txtGameDesc = (TextView)findViewById(R.id.titleSubGameDesc);
        
        spinBarrageStrength = (Spinner)findViewById(R.id.spinBarrageStrength);
        spinBarrageTerrain = (Spinner)findViewById(R.id.spinBarrageTerrain);
    
        listModifiers = (ListView)findViewById(R.id.listBarrageModifiers);
        
		imgBarrageDie1 = (ImageView)findViewById(R.id.imgBarrageDie1);
		imgBarrageDie2 = (ImageView)findViewById(R.id.imgBarrageDie2);
		btnBarrageDiceRoll = (Button)findViewById(R.id.btnBarrageDiceRoll);
		
        txtBarrageResults = (TextView)findViewById(R.id.txtBarrageResults);
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
        
        
		spinBarrageStrength.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                strength = game.getBarrage().getTable().getTable().get(pos);
			    updateResults();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });            
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item, game.getBarrage().getStrengthList());
		adapter1.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
		spinBarrageStrength.setAdapter(adapter1);
		

		spinBarrageTerrain.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                terrain = game.getTerrains().get(pos);
                updateResults();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });            
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item, game.getTerrainList());
		adapter2.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
		spinBarrageTerrain.setAdapter(adapter2);
		
        
        listModifiers.setAdapter(new BarrageModifierListItemAdapter(getApplicationContext(), modifiers));
        listModifiers.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Modifier mod = modifiers.get(position);
                int count = mod.getCount();
                if (count > 0) {
                    count = 0;
                }
                else {
                    count = 1;
                }
                mod.setCount(count);
                
			    updateResults();
            }
        });
        
        
		imgBarrageDie1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    incrementDie(1);
			    displayDice();
			    updateResults();
			}
		});
		imgBarrageDie2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    incrementDie(2);
			    displayDice();
			    updateResults();
			}
		});
		btnBarrageDiceRoll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    audio.play();
			    dice.roll();
			    displayDice();
			    updateResults();
			}
		});
		
		displayStrength();
		displayTerrain();
		displayDice();
	}
    
	void updateResults() {
		String result = game.getBarrage().resolve(dice.getDie(0), dice.getDie(1), strength.getValue().toString(), terrain, modifiers);
        
		txtBarrageResults.setText(result, TextView.BufferType.NORMAL);
	}
	
	void displayStrength() {
		spinBarrageStrength.setSelection(game.getBarrage().getStrengthIndex(strength));
	}
	void displayTerrain() {
		spinBarrageTerrain.setSelection(game.getTerrainIndex(terrain));
	}

	void displayDice() {
		imgBarrageDie1.setImageResource(DiceResources.getWhiteBlack(dice.getDie(0)));
		imgBarrageDie2.setImageResource(DiceResources.getRedWhite(dice.getDie(1)));
	}
 
	void incrementDie(int die) {
		int value = dice.getDie(die-1);
		if (++value > 6) value = 1;
		dice.setDie(die-1, value);
	}
    
    private void navigateUp() {
		startActivity(new Intent(this, MainActivity.class));
	}
}