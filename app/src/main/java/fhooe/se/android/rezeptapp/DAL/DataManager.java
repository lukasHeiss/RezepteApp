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
        InitData();
    }

    @Override
    public void FillAdapter(ArrayAdapter<RecipeData> adapter) {
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
    public void saveRecipe(ArrayAdapter<RecipeData> adapter, RecipeExtendedData recipe) {
        if(recipes == null)
            InitDictionary();

        if(recipe.getId() == -1){
            recipe.setId(counter++);
        }

        recipes.put(recipe.getId(),recipe);
        adapter.add(recipe);
    }








    private void InitData() {
        RecipeExtendedData recipe = new RecipeExtendedData(counter++, "Spaghetti Carbonara", R.drawable.img_carbonara);
        recipe.AddInstruction("Erstmal die Spaghetti kochen");
        recipe.AddInstruction("Währenddessen den Speck zusammen mit dem Knoblauch in etwas Öl anbraten.");
        recipe.AddInstruction("Etwas Nudelwasser aufbehalten, die Nudeln zu Speck und Knoblauch in die Pfanne geben.\n" +
                "Die Eier und den Käse drübergeben, immer wieder gut rühren. Falls es zu trocken wird, etwas Nudelwasser zuschießen.");

        recipe.AddIngredient(new IngredientData(1, 10, "Knoblauch"));
        recipe.AddIngredient(new IngredientData(100, 1, "Speck"));
        recipe.AddIngredient(new IngredientData(2, 14, "Eier"));
        recipes.put(recipe.getId(), recipe);

        recipe = new RecipeExtendedData(counter++, "Einfache Tomatensauce", R.drawable.img_placeholder);
        recipe.AddInstruction("Speck und Zwiebeln anbraten bis die Zwiebeln glasig sind, eventuell mit etwas Weißwein ablöschen.");
        recipe.AddInstruction("Die Tomatenpampe beigeben, mit Paprikapulver, Salz & Pfeffer würzen. Ne Weile köcheln lassen.");
        //recipe.AddIngredient(new IngredientData);
        recipes.put(recipe.getId(), recipe);
    }

}
