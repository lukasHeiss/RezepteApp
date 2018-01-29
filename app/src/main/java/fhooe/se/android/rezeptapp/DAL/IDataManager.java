package fhooe.se.android.rezeptapp.DAL;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import fhooe.se.android.rezeptapp.RecipeDataCallBack;

/**
 * Created by Tom on 11.01.2018.
 */

public interface IDataManager {
    void FillAdapter(ArrayAdapter<Recipe> adapter, Context context);
    void GetRecipeExtended(int recipeId, RecipeDataCallBack callBack);


    void saveRecipe(ArrayAdapter<Recipe> adapter, RecipeExtendedData recipe);
}

