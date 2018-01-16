package fhooe.se.android.rezeptapp.DAL;

import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Tom on 11.01.2018.
 */

public interface IDataManager {
    void FillAdapter(ArrayAdapter<RecipeExtendedData> adapter);
    RecipeExtendedData GetRecipeExtended(RecipeData recipe);


    void saveRecipe(ArrayAdapter<RecipeExtendedData> adapter, RecipeExtendedData recipe);
}

