package ica.SCS.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.*;

import ica.SCS.R;

/**
 * Created by jcapuano on 5/31/2014.
 */
public class ListItemAdapter extends ArrayAdapter<ica.SCS.Core.Game> {
    private final Context context;
    private final List<ica.SCS.Core.Game> values;

    public ListItemAdapter(Context context, List<ica.SCS.Core.Game> values) {
        super(context, R.layout.listitem, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ica.SCS.Core.Game game = values.get(position);

        View rowView = inflater.inflate(R.layout.listitem, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        textView.setText(game.getName() + " - " + game.getDesc());

        int resid = context.getResources().getIdentifier("drawable/" + game.getImage(), null, context.getPackageName());
        imageView.setImageResource(resid);

        return rowView;
    }
}