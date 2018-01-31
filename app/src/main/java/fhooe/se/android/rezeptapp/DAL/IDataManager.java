package fhooe.se.android.rezeptapp.DAL;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;

import fhooe.se.android.rezeptapp.RecipeDataCallBack;

/**
 * Created by Tom on 11.01.2018.
 * interface for the DataManager.
 */

public interface IDataManager {
    /**
     * Used for filling up the entire Adapter in ListView.
     * @param adapter the adapter to be filled with all Recipes
     * @param context Current Application Context.
     */
    void FillAdapter(ArrayAdapter<RecipeData> adapter, Context context);

    ArrayAdapter<RecipeData>GetAdapter(Activity activity, Context context, View.OnClickListener listener);

    /**
     * Gets the entire RecipeDataExtended for a specified recipe.
     * implement RecipeDataCallBack and pass this as the callback to use this function correctly.
     * @param recipe the RecipeData that should be extended
     * @param callBack  the Callback function that is called when the recipe has been fetched.
     */
    void GetRecipeExtended(RecipeData recipe, RecipeDataCallBack callBack);


    void GetRecipeExtended(int recipeId, RecipeDataCallBack callBack);
    /**
     * Saves the passed RecipeDataExtended to the database and the passed adapter.
     * Handles Insert as well as Update.
     * @param adapter the adapter the Recipe should also be saved to (with the correct id)
     * @param recipe the RecipeDataExtended to be saved.
     */
    void saveRecipe(ArrayAdapter<RecipeData> adapter, RecipeExtendedData recipe);

    void DeleteRecipe(RecipeData recipe);
}

