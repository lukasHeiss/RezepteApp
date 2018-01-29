package fhooe.se.android.rezeptapp.DAL;

import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import fhooe.se.android.rezeptapp.R;

/**
 * Created by Tom on 24.01.2018.
 */

public class IngredientDataAdapter extends ArrayAdapter<Ingredient> {
    private View.OnClickListener mListener;

    public IngredientDataAdapter(Context _c, View.OnClickListener _listener) {
        super(_c, -1);
        mListener = _listener;
    }


    @Override
    public View getView(int _pos, View _view, ViewGroup _parent) {
        if (_view == null){
            Context c = getContext();
            LayoutInflater inflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            _view = inflater.inflate(R.layout.activity_recipe_ingredientelement, null);
        }
        final Ingredient data = getItem(_pos);
        if (data != null){
            //Log.e("IngredientAdapter", "" + data. + data.getUnit() + data.getIngredient());
            TextView tv = (TextView)_view.findViewById(R.id.activity_recipe_ingredientelement_number);
            tv.setText (String.valueOf(data.amount) + String.valueOf(data.unit));

            TextView tv2 = (TextView)_view.findViewById(R.id.activity_recipe_ingredientelement_text);
            tv2.setText(data.ingredient);
        }

        return _view;
        //return super.getView(position, convertView, parent);
    }
}
