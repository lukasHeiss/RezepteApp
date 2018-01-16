package fhooe.se.android.rezeptapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import fhooe.se.android.rezeptapp.DAL.DALFactory;
import fhooe.se.android.rezeptapp.DAL.IDataManager;
import fhooe.se.android.rezeptapp.DAL.RecipeData;
import fhooe.se.android.rezeptapp.DAL.RecipeDataAdapter;
import fhooe.se.android.rezeptapp.DAL.RecipeExtendedData;

public class ListActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static final String TAG="Rezepte-Liste";
    private static IDataManager dataManager;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        adapter = new RecipeDataAdapter(this, this);
        dataManager = DALFactory.GetDataManager();
        dataManager.FillAdapter(adapter);

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
        dataManager.saveRecipe(adapter, new RecipeExtendedData(-1, "Tomatensauce", R.drawable.img_placeholder));
    }

    @Override
    public void onClick(View _view) {
        Toast.makeText(this,"Properties has been selected", Toast.LENGTH_SHORT).show();
    }
}
