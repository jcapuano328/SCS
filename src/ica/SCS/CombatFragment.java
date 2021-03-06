package ica.SCS;

import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.content.*;
import android.database.DataSetObserver;
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

import ica.SCS.Adapters.CombatModifierListItemAdapter;
import ica.SCS.Core.*;
import ica.SCS.Helpers.*;

/**
 * Created by jcapuano on 6/4/2014.
 */
public class CombatFragment extends Fragment {
    
    private View rootView;
    
	private Button btnAttackerPrev;
	private Button btnAttackerNext;
	private EditText editAttacker;
	
	private Button btnDefenderPrev;
	private Button btnDefenderNext;
	private EditText editDefender;
    
    private Spinner spinCombatOdds;
    private Spinner spinCombatTerrain;
    private Spinner spinCombatTerrainBtween;
    
    private ListView listModifiers;
    private CombatModifierListItemAdapter adapterModifiers;
    
	private ImageView imgCombatDie1;
	private ImageView imgCombatDie2;
	private Button btnCombatDiceRoll;
	
	private TextView txtCombatResults;
    
    private Game game;
    private Dice dice;
	private PlayAudio audio;
    private Results<Integer> odds;
    private Terrain terrain;
    private Terrain terrainBtween;
    private ArrayList<CombatModifier> modifiers;

    
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
        
            modifiers = game.getCombat().getModifiers();
            odds = game.getCombat().getDefaultOdds();
            terrain = game.getDefaultTerrain();
            terrainBtween = game.getDefaultTerrain();
    
            rootView = inflater.inflate(R.layout.combat, container, false);
        
		    // attacker
		    btnAttackerPrev = (Button)rootView.findViewById(R.id.btnCombatAttackerPrev);
		    btnAttackerNext = (Button)rootView.findViewById(R.id.btnCombatAttackerNext);
		    editAttacker = (EditText)rootView.findViewById(R.id.editCombatAttacker);
		    editAttacker.setText("1");
		
		    // defender
		    btnDefenderPrev = (Button)rootView.findViewById(R.id.btnCombatDefenderPrev);
		    btnDefenderNext = (Button)rootView.findViewById(R.id.btnCombatDefenderNext);
		    editDefender = (EditText)rootView.findViewById(R.id.editCombatDefender);
		    editDefender.setText("1");
        
            spinCombatTerrain = (Spinner)rootView.findViewById(R.id.spinCombatTerrain);
            spinCombatTerrainBtween = (Spinner)rootView.findViewById(R.id.spinCombatTerrainBtween);
            spinCombatOdds = (Spinner)rootView.findViewById(R.id.spinCombatOdds);
    
            listModifiers = (ListView)rootView.findViewById(R.id.listCombatModifiers);
        
		    imgCombatDie1 = (ImageView)rootView.findViewById(R.id.imgCombatDie1);
		    imgCombatDie2 = (ImageView)rootView.findViewById(R.id.imgCombatDie2);
		    btnCombatDiceRoll = (Button)rootView.findViewById(R.id.btnCombatDiceRoll);
		
            txtCombatResults = (TextView)rootView.findViewById(R.id.txtCombatResults);
            
		    // attacker
		    btnAttackerPrev.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        double value = getAttacker();
			        if (--value < 1) value = 1;
			        editAttacker.setText(Double.toString(value));
			        //calcOdds();
			        //updateResults();
			    }
		    });
		    btnAttackerNext.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        double value = getAttacker();
			        editAttacker.setText(Double.toString(++value));
			        //calcOdds();
			        //updateResults();
			    }
		    });
		    editAttacker.addTextChangedListener(new TextWatcher() {
			    @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
			    @Override
                public void onTextChanged(CharSequence s, int start, int before, int end) {
                }
			    @Override
                public void afterTextChanged(Editable s) {
			        calcOdds();
			        updateResults();
                }
            });
        
		    // defender
		    btnDefenderPrev.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        double value = getDefender();
			        if (--value < 1) value = 1;
			        editDefender.setText(Double.toString(value));
			        //calcOdds();
			        //updateResults();
			    }
		    });
		    btnDefenderNext.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        double value = getDefender();
			        editDefender.setText(Double.toString(++value));
			        //calcOdds();
			        //updateResults();
			    }
		    });
		    editDefender.addTextChangedListener(new TextWatcher() {
			    @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
			    @Override
                public void onTextChanged(CharSequence s, int start, int before, int end) {
                }
                public void afterTextChanged(Editable s) {
			        calcOdds();
			        updateResults();
                }
            });

            spinCombatTerrain.setAdapter(new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_dropdown_item, game.getTerrainList()));
		    spinCombatTerrain.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    terrain = game.getTerrains().get(pos);
                    calcOdds();
			        updateResults();
                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });            
		        
            spinCombatTerrainBtween.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, game.getTerrainList()));
		    spinCombatTerrainBtween.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    terrainBtween = game.getTerrains().get(pos);
                    calcOdds();
			        updateResults();
                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });            

		    spinCombatOdds.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, game.getCombat().getOddsList()));
		    spinCombatOdds.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    odds = game.getCombat().getOddsByIndex(pos);
                    updateResults();
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });

            adapterModifiers = new CombatModifierListItemAdapter(getActivity().getApplicationContext(), modifiers);
            adapterModifiers.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    calcOdds();
                    updateResults();
                }
            });
            listModifiers.setAdapter(adapterModifiers);

		    imgCombatDie1.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        incrementDie(1);
			        displayDice();
			        updateResults();
			    }
		    });
		    imgCombatDie2.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        incrementDie(2);
			        displayDice();
			        updateResults();
			    }
		    });
		    btnCombatDiceRoll.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        audio.play();
			        dice.roll();
			        displayDice();
			        updateResults();
			    }
		    });

            calcOdds();
		    displayTerrains();
		    displayDice();
            
        }
        return rootView;
    }
    
	void updateResults() {
		String result = game.getCombat().resolve(dice.getDie(0), dice.getDie(1), odds.getValue(), getAttackerModifiers(), getDefenderModifiers(), terrain, terrainBtween);
		txtCombatResults.setText(result, TextView.BufferType.NORMAL);
	}
	
	void calcOdds() {
        int o = game.getCombat().calculate(getAttacker(), getDefender(), getAttackerModifiers(), getDefenderModifiers(), terrain, terrainBtween);
        odds = game.getCombat().getOdds(o);
		displayOdds();
	}
    
    private ArrayList<Modifier> getAttackerModifiers() {
        ArrayList<Modifier> mods = new ArrayList<Modifier>();
        for (CombatModifier cm : modifiers) {
            if (cm.getCount() > 0) {
                Modifier m = new Modifier(cm);
                mods.add(m);
            }
        }
        return mods;
    }
    
    private ArrayList<Modifier> getDefenderModifiers() {
        ArrayList<Modifier> mods = new ArrayList<Modifier>();
        for (CombatModifier cm : modifiers) {
            if (cm.getDefenderCount() > 0) {
                Modifier m = new Modifier(cm);
                m.setCount(cm.getDefenderCount());
                mods.add(m);
            }
        }
        return mods;
    }
    
	void displayOdds() {
		spinCombatOdds.setSelection(game.getCombat().getOddsIndex(odds));
	}
	void displayTerrains() {
		spinCombatTerrain.setSelection(game.getTerrainIndex(terrain));
		spinCombatTerrainBtween.setSelection(game.getTerrainIndex(terrainBtween));
	}

	void displayDice() {
		imgCombatDie1.setImageResource(DiceResources.getWhiteBlack(dice.getDie(0)));
		imgCombatDie2.setImageResource(DiceResources.getRedWhite(dice.getDie(1)));
	}
 
	void incrementDie(int die) {
		int value = dice.getDie(die-1);
		if (++value > 6) value = 1;
		dice.setDie(die-1, value);
	}
    
	double getAttacker() {
        String v = editAttacker.getText().toString();
        if (!v.isEmpty())
            return Double.parseDouble(v);
        return 1;           
	}
	double getDefender() {
        String v = editDefender.getText().toString();
        if (!v.isEmpty())
            return Double.parseDouble(v);
        return 1;           
	}
}