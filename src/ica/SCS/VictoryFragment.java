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

import ica.SCS.Core.*;
import ica.SCS.Helpers.*;

/**
 * Created by jcapuano on 6/7/2014.
 */
public class VictoryFragment extends Fragment {
    private View rootView;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            final ViewParent parent = rootView.getParent();
            if (parent != null && parent instanceof ViewGroup)
                ((ViewGroup) parent).removeView(rootView);  
        }
        else {
            Intent intent = getActivity().getIntent();
            game = Scs.getGame(intent.getIntExtra ("Game", -1));
            saved = Scs.getSaved(game);
        
            rootView = inflater.inflate(R.layout.victory, container, false);
        
		    // attacker
		    txtPlayer1 = (TextView)rootView.findViewById(R.id.txtPlayer1);
		    btnPlayer1VPPrev = (Button)rootView.findViewById(R.id.btnPlayer1VPPrev);
		    btnPlayer1VPNext = (Button)rootView.findViewById(R.id.btnPlayer1VPNext);
		    editPlayer1VP = (EditText)rootView.findViewById(R.id.editPlayer1VP);
		    editPlayer1VP.setText("0");
		
		    // defender
		    txtPlayer2 = (TextView)rootView.findViewById(R.id.txtPlayer2);
		    btnPlayer2VPPrev = (Button)rootView.findViewById(R.id.btnPlayer2VPPrev);
		    btnPlayer2VPNext = (Button)rootView.findViewById(R.id.btnPlayer2VPNext);
		    editPlayer2VP = (EditText)rootView.findViewById(R.id.editPlayer2VP);
		    editPlayer2VP.setText("0");
        
        
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
        return rootView;
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
}