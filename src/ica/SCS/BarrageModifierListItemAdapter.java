package ica.SCS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import ica.SCS.Core.*;

import java.util.*;
/**
 * Created by jcapuano on 5/31/2014.
 */
public class BarrageModifierListItemAdapter extends ArrayAdapter<Modifier> {
    private final Context context;
    private final List<Modifier> values;

    public BarrageModifierListItemAdapter(Context context, List<Modifier> values) {
        super(context, R.layout.barragemodifierlistitem, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Modifier mod = values.get(position);

        View rowView = inflater.inflate(R.layout.barragemodifierlistitem, parent, false);
        
        CheckBox chkBarrageModifier = (CheckBox) rowView.findViewById(R.id.chkBarrageModifier);
        chkBarrageModifier.setChecked(mod.getCount() > 0);
        chkBarrageModifier.setText(mod.getName());

        return rowView;
    }
}