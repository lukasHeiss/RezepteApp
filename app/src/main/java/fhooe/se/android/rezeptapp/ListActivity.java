package fhooe.se.android.rezeptapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static final String TAG="Rezepte-Liste";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ArrayAdapter adapter = new RecipeDataAdapter(this, this);

        adapter.add(new RecipeData(1,"Pizza", -1));
        adapter.add(new RecipeData(1,"Tom", -1));

        ListView lv = (ListView)findViewById(R.id.activity_list_mainview);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> _adapterView,
                            View _view,
                            int _i,
                            long _l) {
        RecipeData data = (RecipeData) _adapterView.getAdapter().getItem(_i);
        Toast.makeText(this, data.getRecipeName() + " has been selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View _view) {
        Toast.makeText(this,"Properties has been selected", Toast.LENGTH_SHORT).show();
    }
}
