package ica.SCS;

import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.RadioGroup.OnCheckedChangeListener;
import ica.SCS.Core.*;
import ica.SCS.Helpers.*;
import java.util.*;

/**
 * Created by jcapuano on 6/8/2014.
 */
public class DopActivity extends Activity {
	private ImageView imgBack;
	private ImageView imgScs;
	private TextView txtGameName;
	private TextView txtGameDesc;
    
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
    public void onCreate(Bundle savedInstanceState) {
		dice = new Dice(1, 1, 6);
		audio = new PlayAudio (this);
        
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.dop);
        
        Intent intent = getIntent();
        game = Scs.getGame(intent.getIntExtra ("Game", -1));
        
		imgBack = (ImageView)findViewById(R.id.titleSubScsBack);
		imgScs = (ImageView)findViewById(R.id.titleSubScs);
		txtGameName = (TextView)findViewById(R.id.titleSubGameName);
		txtGameDesc = (TextView)findViewById(R.id.titleSubGameDesc);

        //radioDop = (RadioGroup) findViewById(R.id.radioDop);
        radioDopReconstitution = (RadioButton) findViewById(R.id.radioDopReconstitution);
        chkDopInCommand = (CheckBox) findViewById(R.id.chkDopInCommand);
        radioDopForceMarch = (RadioButton) findViewById(R.id.radioDopForceMarch);
        
		imgDopDie1 = (ImageView)findViewById(R.id.imgDopDie1);
		btnDopDiceRoll = (Button)findViewById(R.id.btnDopDiceRoll);
		
        txtDopResults = (TextView)findViewById(R.id.txtDopResults);
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
    
    private void navigateUp() {
		Intent gameDetail = new Intent (this, GameActivity.class);
		gameDetail.putExtra("Game", game.getId());
		startActivity (gameDetail);
	}
}