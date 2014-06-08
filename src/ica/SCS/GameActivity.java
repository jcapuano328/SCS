package ica.SCS;

import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import ica.SCS.Core.*;

/**
 * Created by jcapuano on 5/18/2014.
 */
public class GameActivity extends Activity {
	private ImageView imgBack;
	private ImageView imgScs;
	private TextView txtGameName;
	private TextView txtGameDesc;
	private Button btnReset;
	private TextView txtTurn;
	private Button btnTurnPrev;
	private Button btnTurnNext;
	private TextView txtPhase;
	private Button btnPhasePrev;
	private Button btnPhaseNext;
	private ImageButton btnBarrage;
	private ImageButton btnCombat;
	private ImageButton btnCustom;
	private ImageButton btnVictory;
	private Game game;
    private Saved saved;
    private Activity me;

    @Override
    public void onCreate (Bundle bundle) {
        me = this;
        
        super.onCreate(bundle);

        Intent intent = getIntent();

        game = Scs.getGame(intent.getIntExtra ("Game", -1));
        saved = Scs.getSaved(game);

        setContentView(R.layout.game);		

		imgBack = (ImageView)findViewById(R.id.titleSubScsBack);
		imgScs = (ImageView)findViewById(R.id.titleSubScs);

		// title
		txtGameName = (TextView)findViewById(R.id.titleSubGameName);
		txtGameDesc = (TextView)findViewById(R.id.titleSubGameDesc);

		// current turn
		txtTurn = (TextView)findViewById(R.id.textTurn);
		btnTurnPrev = (Button)findViewById(R.id.btnTurnPrev);
		btnTurnNext = (Button)findViewById(R.id.btnTurnNext);

		// current phase
		txtPhase = (TextView)findViewById(R.id.textPhase);
		btnPhasePrev = (Button)findViewById(R.id.btnPhasePrev);
		btnPhaseNext = (Button)findViewById(R.id.btnPhaseNext);

		btnReset = (Button)findViewById(R.id.btnReset);

		btnBarrage = (ImageButton)findViewById(R.id.btnBarrage);
		btnCombat = (ImageButton)findViewById(R.id.btnCombat);
		btnCustom = (ImageButton)findViewById(R.id.btnCustom);
		btnVictory = (ImageButton)findViewById(R.id.btnVictory);
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

		btnReset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    saved.reset(game);
			    update();
			    save();
			}
		});        
			
		btnTurnPrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Scs.prevTurn(game, saved);
			    update();
			    save(); 
			}
		});        
        
		btnTurnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
		    	Scs.nextTurn(game, saved);
			    update();
			    save(); 
			}
		});        

		btnPhasePrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
    			Scs.prevPhase(game, saved);
			    update();
			    save(); 
			}
		});        
        
		btnPhaseNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
    			Scs.nextPhase(game, saved);
			    update();
			    save(); 
			}
		});        

		btnBarrage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent barrageDetail = new Intent (me, BarrageActivity.class);
                barrageDetail.putExtra("Game", game.getId());

                startActivity (barrageDetail);
            }
        });

		btnCombat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
			    Intent combatDetail = new Intent (me, CombatActivity.class);
			    combatDetail.putExtra("Game", game.getId());

			    startActivity (combatDetail);
            }
        });
			
		btnCustom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            }
        });
		
		btnVictory.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
			    Intent victoryDetail = new Intent (me, VictoryActivity.class);
			    victoryDetail.putExtra("Game", game.getId());

			    startActivity (victoryDetail);
            }
        });
        
		update();
	}
	
    private void update() {
		txtTurn.setText(Scs.getCurrentTurn(game, saved));
		txtPhase.setText(Scs.getCurrentPhase(game, saved));
	}
	
    private void save() {
        Scs.saveSaved();
	}

    private void navigateUp() {
		startActivity(new Intent(this, MainActivity.class));
	}

}
