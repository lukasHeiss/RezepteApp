package fhooe.se.android.rezeptapp.DAL;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import fhooe.se.android.rezeptapp.R;
import fhooe.se.android.rezeptapp.RecipeActivity;

/**
 * Created by Lukas on 11.01.18.
 * Adapter for basic recipes.
 * Possibly change underlying data type to RecipeData, so no more Data-Access classes are used as Domain classes.
 * This would break some stuff in DataManager, so I'm not touching anything.
 */

public class RecipeDataAdapter extends ArrayAdapter<Recipe>  {
    private View.OnClickListener mListener;


    public RecipeDataAdapter(Context _c, View.OnClickListener _listener) {
        super(_c, -1);
        mListener = _listener;
    }

    public int getRecipeId(int _pos){
        return getItem(_pos).id;
    }

    @Override
    public View getView(int _pos, View _view, ViewGroup _parent) {
        if (_view == null){
            Context c = getContext();
            LayoutInflater inflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            _view = inflater.inflate(R.layout.activity_list_element, null);
        }
        final Recipe data = getItem(_pos);
        if (data != null){
            TextView tv = null;
            tv = (TextView)_view.findViewById(R.id.activity_list_element_textview_name);
            tv.setText(data.recipeName);

            ImageView iv = null;
            iv=(ImageView) _view.findViewById(R.id.activity_list_element_icon);
            iv.setImageResource(data.icon);
        }

        return _view;
        //return super.getView(position, convertView, parent);
    }

}
