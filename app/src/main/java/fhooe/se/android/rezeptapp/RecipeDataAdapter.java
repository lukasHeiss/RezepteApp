package fhooe.se.android.rezeptapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Lukas on 11.01.18.
 */

public class RecipeDataAdapter extends ArrayAdapter<RecipeData> {
    private View.OnClickListener mListener;


    public RecipeDataAdapter(Context _c, View.OnClickListener _listener) {
        super(_c, -1);
        mListener = _listener;
    }


    @Override
    public View getView(int _pos, View _view, ViewGroup _parent) {
        if (_view == null){
            Context c = getContext();
            LayoutInflater inflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            _view = inflater.inflate(R.layout.activity_list_element, null);
        }
        final RecipeData data = getItem(_pos);
        if (data != null){
            TextView tv = null;
            tv = (TextView)_view.findViewById(R.id.activity_list_element_textview_name);
            tv.setText(data.getRecipeName());
            ImageView iv = null;
            iv=(ImageView) _view.findViewById(R.id.activity_list_element_icon);
            iv.setImageResource(data.getIcon());
        }

        return _view;
        //return super.getView(position, convertView, parent);
    }
}
