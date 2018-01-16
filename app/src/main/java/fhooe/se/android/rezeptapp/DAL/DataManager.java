package fhooe.se.android.rezeptapp.DAL;

import android.util.Log;
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
        recipes.put(counter, new RecipeExtendedData(counter, "Spaghetti Carbonara",  R.drawable.img_carbonara));
        counter++;
        recipes.put(counter, new RecipeExtendedData(counter, "Pizza Teig", R.drawable.img_placeholder));
        counter++;
    }

    @Override
    public void FillAdapter(ArrayAdapter<RecipeExtendedData> adapter) {
        if(recipes == null) InitDictionary();
        adapter.addAll(recipes.values());
    }

    @Override
    public RecipeExtendedData GetRecipeExtended(int recipeId) {
        if(recipes == null) InitDictionary();
        Log.e("whatev", "GetRecipeExtended for " + recipeId);
        return recipes.get(recipeId);
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
