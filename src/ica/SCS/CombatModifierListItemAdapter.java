package ica.SCS;

import android.content.Context;
import android.view.LayoutInflater;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;

import ica.SCS.Core.*;

import java.util.*;
/**
 * Created by jcapuano on 5/31/2014.
 */
public class CombatModifierListItemAdapter extends ArrayAdapter<CombatModifier> {
    private final Context context;
    private final List<CombatModifier> values;
    private CombatModifierListItemAdapter me;

    public CombatModifierListItemAdapter(Context context, List<CombatModifier> values) {
        super(context, R.layout.combatmodifierlistitem, values);
        this.context = context;
        this.values = values;
        me = this;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        CombatModifier mod = values.get(position);

        View rowView = inflater.inflate(R.layout.combatmodifierlistitem, parent, false);
        
        TextView lblCombatModifier = (TextView) rowView.findViewById(R.id.lblCombatModifier);
        lblCombatModifier.setText(mod.getName());
        
	    Button btnCombatModAttackerPrev = (Button)rowView.findViewById(R.id.btnCombatModAttackerPrev);
	    Button btnCombatModAttackerNext = (Button)rowView.findViewById(R.id.btnCombatModAttackerNext);
	    EditText editCombatModAttacker = (EditText)rowView.findViewById(R.id.editCombatModAttacker);
        btnCombatModAttackerPrev.setTag(position);
		btnCombatModAttackerPrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                int pos = (Integer)arg0.getTag();
                CombatModifier m = values.get(pos);
                int c = m.getCount() - 1;
                m.setCount(c >= 0 ? c : 0);
                me.notifyDataSetChanged();
			}
		});
        btnCombatModAttackerNext.setTag(position);
        btnCombatModAttackerNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                int pos = (Integer)arg0.getTag();
                CombatModifier m = values.get(pos);
                m.setCount(m.getCount() + 1);
                me.notifyDataSetChanged();
			}
		});
		editCombatModAttacker.setText(Integer.toString(mod.getCount()));
        editCombatModAttacker.addTextChangedListener(new TextWatcher() {
			@Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
			@Override
            public void onTextChanged(CharSequence s, int start, int before, int end) {
            }
			@Override
            public void afterTextChanged(Editable s) {
                me.notifyDataSetChanged();
            }
        });
	
	    Button btnCombatModDefenderPrev = (Button)rowView.findViewById(R.id.btnCombatModDefenderPrev);
	    Button btnCombatModDefenderNext = (Button)rowView.findViewById(R.id.btnCombatModDefenderNext);
	    EditText editCombatModDefender = (EditText)rowView.findViewById(R.id.editCombatModDefender);
        btnCombatModDefenderPrev.setTag(position);
		btnCombatModDefenderPrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                int pos = (Integer)arg0.getTag();
                CombatModifier m = values.get(pos);
                int c = m.getDefenderCount() - 1;
                m.setDefenderCount(c >= 0 ? c : 0);
                me.notifyDataSetChanged();
			}
		});
        btnCombatModDefenderNext.setTag(position);
        btnCombatModDefenderNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                int pos = (Integer)arg0.getTag();
                CombatModifier m = values.get(pos);
                m.setDefenderCount(m.getDefenderCount() + 1);
                me.notifyDataSetChanged();
			}
		});
        editCombatModDefender.setText(Integer.toString(mod.getDefenderCount()));
        editCombatModDefender.addTextChangedListener(new TextWatcher() {
			@Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
			@Override
            public void onTextChanged(CharSequence s, int start, int before, int end) {
            }
			@Override
            public void afterTextChanged(Editable s) {
                me.notifyDataSetChanged();
                
            }
        });

        return rowView;
    }
}