package ica.SCS;

import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.content.*;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.*;

import ica.SCS.Adapters.BarrageModifierListItemAdapter;
import ica.SCS.Core.*;
import ica.SCS.Helpers.*;


/**
 * Created by jcapuano on 6/1/2014.
 */
public class BarrageFragment extends Fragment {

    private View rootView;
    
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            final ViewParent parent = rootView.getParent();
            if (parent != null && parent instanceof ViewGroup)
                ((ViewGroup) parent).removeView(rootView);  
        }
        else {
		    dice = new Dice(2, 1, 6);
		    audio = new PlayAudio (getActivity());
        
            Intent intent = getActivity().getIntent();
            game = Scs.getGame(intent.getIntExtra ("Game", -1));
        
            modifiers = game.getBarrage().getTable().cloneModifiers();
            strength = game.getBarrage().getDefaultStrength();
            terrain = game.getDefaultTerrain();
    
            rootView = inflater.inflate(R.layout.barrage, container, false);
        
            spinBarrageStrength = (Spinner)rootView.findViewById(R.id.spinBarrageStrength);
            spinBarrageTerrain = (Spinner)rootView.findViewById(R.id.spinBarrageTerrain);
    
            listModifiers = (ListView)rootView.findViewById(R.id.listBarrageModifiers);
        
		    imgBarrageDie1 = (ImageView)rootView.findViewById(R.id.imgBarrageDie1);
		    imgBarrageDie2 = (ImageView)rootView.findViewById(R.id.imgBarrageDie2);
		    btnBarrageDiceRoll = (Button)rootView.findViewById(R.id.btnBarrageDiceRoll);
		
            txtBarrageResults = (TextView)rootView.findViewById(R.id.txtBarrageResults);
            
		    spinBarrageStrength.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    strength = game.getBarrage().getTable().getTable().get(pos);
			        updateResults();
                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });            
		    ArrayAdapter<String> adapter1 = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_dropdown_item, game.getBarrage().getStrengthList());
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
		    ArrayAdapter<String> adapter2 = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_dropdown_item, game.getTerrainList());
		    adapter2.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
		    spinBarrageTerrain.setAdapter(adapter2);
		
        
            listModifiers.setAdapter(new BarrageModifierListItemAdapter(getActivity().getApplicationContext(), modifiers));
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
        return rootView;
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
}