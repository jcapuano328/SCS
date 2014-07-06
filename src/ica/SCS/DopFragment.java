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
import android.widget.RadioGroup.OnCheckedChangeListener;

import java.util.*;

import ica.SCS.Core.*;
import ica.SCS.Helpers.*;

/**
 * Created by jcapuano on 6/8/2014.
 */
public class DopFragment extends Fragment {
    private View rootView;

    //private RadioGroup radioDop;
    private RadioButton radioDopReconstitution;
    private CheckBox chkDopInCommand;
    private RadioButton radioDopForceMarch;
    
	private ImageView imgDopDie1;
	private Button btnDopDiceRoll;
	private TextView txtDopResults;
    
    private Game game;
    private Dice dice;
	private PlayAudio audio;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            final ViewParent parent = rootView.getParent();
            if (parent != null && parent instanceof ViewGroup)
                ((ViewGroup) parent).removeView(rootView);  
        }
        else {
		    dice = new Dice(1, 1, 6);
		    audio = new PlayAudio (getActivity());
            Intent intent = getActivity().getIntent();
            game = Scs.getGame(intent.getIntExtra ("Game", -1));
        
            rootView = inflater.inflate(R.layout.dop, container, false);
        
            //radioDop = (RadioGroup) rootView.findViewById(R.id.radioDop);
            radioDopReconstitution = (RadioButton) rootView.findViewById(R.id.radioDopReconstitution);
            chkDopInCommand = (CheckBox) rootView.findViewById(R.id.chkDopInCommand);
            radioDopForceMarch = (RadioButton) rootView.findViewById(R.id.radioDopForceMarch);
        
		    imgDopDie1 = (ImageView)rootView.findViewById(R.id.imgDopDie1);
		    btnDopDiceRoll = (Button)rootView.findViewById(R.id.btnDopDiceRoll);
		
            txtDopResults = (TextView)rootView.findViewById(R.id.txtDopResults);
            
            /*
            radioDop.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    chkDopInCommand.setEnabled((checkedId == R.id.radioDopReconstitution));
                }
            });
            */
            radioDopReconstitution.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    chkDopInCommand.setEnabled(true);
                    radioDopForceMarch.setChecked(false);
			    }
		    });        
        
            radioDopForceMarch.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    radioDopReconstitution.setChecked(false);
                    chkDopInCommand.setEnabled(false);
			    }
		    });        
        
		    imgDopDie1.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        incrementDie(1);
			        displayDice();
			        updateResults();
			    }
		    });
		    btnDopDiceRoll.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        audio.play();
			        dice.roll();
			        displayDice();
			        updateResults();
			    }
		    });
        
            //radioDop.check(R.id.radioDopReconstitution);
            radioDopReconstitution.setChecked(true);
        
		    displayDice();
        }
        return rootView;
    }
    
	void updateResults() {
        // get selected radio button from radioGroup
        //int selectedId = radioDop.getCheckedRadioButtonId();
        //String result = (selectedId == R.id.radioDopReconstitution) ? resolveReconstitution() : resolveForceMarch();
        String result = radioDopReconstitution.isChecked() ? resolveReconstitution() : resolveForceMarch();
		txtDopResults.setText(result, TextView.BufferType.NORMAL);
	}
	
    private String resolveReconstitution() {
        int die = dice.getDie(0);
        if (chkDopInCommand.isChecked()) {
            die *= 2;
        }
		else {	
			die *= 3;
        }            
        
        Saved saved = Scs.getSaved(game);
        return Scs.offsetTurn(game, saved, die);
    }
    
    private String resolveForceMarch() {
        int die = dice.getDie(0);
        return (die >= 5) ? "Exploit" : "Normal";
    }
    
	void displayDice() {
		imgDopDie1.setImageResource(DiceResources.getWhiteBlack(dice.getDie(0)));
	}
 
	void incrementDie(int die) {
		int value = dice.getDie(die-1);
		if (++value > 6) value = 1;
		dice.setDie(die-1, value);
	}
}