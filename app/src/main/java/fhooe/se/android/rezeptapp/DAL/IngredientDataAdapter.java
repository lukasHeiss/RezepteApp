package fhooe.se.android.rezeptapp.DAL;

import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import fhooe.se.android.rezeptapp.R;

/**
 * Created by Tom on 24.01.2018.
 * Adapter for Ingredients.
 */

public class IngredientDataAdapter extends ArrayAdapter<IngredientData> {
    private View.OnClickListener mListener;
    boolean isEditable;

    public IngredientDataAdapter(Context _c, View.OnClickListener _listener, boolean isEditable) {
        super(_c, -1);
        mListener = _listener;
        this.isEditable = isEditable;
    }


    @Override
    public View getView(int _pos, View _view, ViewGroup _parent) {
        if (_view == null){
            Context c = getContext();
            LayoutInflater inflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(isEditable)
                _view = inflater.inflate(R.layout.activity_recipe_edit_ingredientelement, null);
            else
                _view = inflater.inflate(R.layout.activity_recipe_ingredientelement, null);
        }
        final IngredientData data = getItem(_pos);
        if (data != null && !isEditable){
            //Log.e("IngredientAdapter", "" + data. + data.getUnit() + data.getIngredient());

            TextView tv = (TextView)_view.findViewById(R.id.activity_recipe_ingredientelement_number);
            tv.setText (String.format(Locale.getDefault(),"%.0f%s", data.getAmount(), String.valueOf(data.getUnit())));

            TextView tv2 = (TextView)_view.findViewById(R.id.activity_recipe_ingredientelement_text);
            tv2.setText(data.getIngredient());
        }

        return _view;
        //return super.getView(position, convertView, parent);
    }
}
