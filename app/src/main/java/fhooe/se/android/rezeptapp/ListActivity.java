package fhooe.se.android.rezeptapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import fhooe.se.android.rezeptapp.DAL.DALFactory;
import fhooe.se.android.rezeptapp.DAL.DataManager;
import fhooe.se.android.rezeptapp.DAL.IDataManager;
import fhooe.se.android.rezeptapp.DAL.IngredientData;
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
        dataManager.FillAdapter(adapter, this);

        ListView lv = (ListView)findViewById(R.id.activity_list_mainview);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);


        Button button = (Button)findViewById(R.id.activity_list_addNew);
        button.setText("Add Käsebrot");
        button.setOnClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> _adapterView,
                            View _view,
                            int _i,
                            long _l) {

        int recipeId = ((RecipeDataAdapter)_adapterView.getAdapter()).getRecipeId(_i);
        //Log.i(TAG, "Set extra " + data.getId() + " " + data.getRecipeName());
        Intent i = new Intent(getApplicationContext(), RecipeActivity.class);
        i.putExtra("recipe", recipeId);
        startActivity(i);
    }

    @Override
    public void onClick(View _view) {
        RecipeExtendedData recipe = new RecipeExtendedData("Käsebrot", R.drawable.img_placeholder);
        recipe.setDifficulty(R.string.diff_beginner);
        recipe.setTimePreparation(1);
        recipe.AddIngredient(new IngredientData(1, "Scheibe", "Käse"));
        recipe.AddIngredient(new IngredientData(1, "Scheibe", "Brot"));
        recipe.AddInstruction("Die Käsescheibe auf das Brot legen. Tadaaaa!");
        dataManager.saveRecipe(adapter, recipe);
        //Toast.makeText(this,"Properties has been selected", Toast.LENGTH_SHORT).show();
    }
}
