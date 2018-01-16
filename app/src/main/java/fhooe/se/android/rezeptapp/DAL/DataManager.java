package fhooe.se.android.rezeptapp.DAL;

import android.widget.ArrayAdapter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import fhooe.se.android.rezeptapp.R;

/**
 * Created by Tom on 11.01.2018.
 * Saves and manages All recipes.
 * Makes it easier to possibly later switch to a database driven solution
 */

public class DataManager implements IDataManager {
    private static int counter = 0;
    private Map<Integer, RecipeExtendedData> recipes;

    private void InitDictionary() {
        recipes = new HashMap<Integer, RecipeExtendedData>();
        recipes.put(0, new RecipeExtendedData(0, "Spaghetti Carbonara",  R.drawable.img_carbonara));
        recipes.put(1, new RecipeExtendedData(1, "Pizza Teig", R.drawable.img_pizza_dough));
    }

    @Override
    public void FillAdapter(ArrayAdapter<RecipeExtendedData> adapter) {
        if(recipes == null) InitDictionary();
        adapter.addAll(recipes.values());
    }

    @Override
    public RecipeExtendedData GetRecipeExtended(RecipeData recipe) {
        return null;
    }

    @Override
    public void saveRecipe(ArrayAdapter<RecipeExtendedData> adapter, RecipeExtendedData recipe) {
        if(recipes == null)
            InitDictionary();

        if(recipe.getId() == -1){
            recipe.setId(counter++);
        }

        recipes.put(recipe.getId(),recipe);
        adapter.add(recipe);
    }

}
