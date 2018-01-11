package fhooe.se.android.rezeptapp.DAL;

import fhooe.se.android.rezeptapp.RecipeData;
import fhooe.se.android.rezeptapp.RecipeDataAdapter;
import fhooe.se.android.rezeptapp.RecipeExtendedData;

/**
 * Created by Tom on 11.01.2018.
 */

public interface DaoInterface {
    RecipeDataAdapter GetRecipeAdapter();
    RecipeExtendedData GetRecipeExtended(RecipeData recipe);

    void AddRecipe(RecipeExtendedData recipe);
    void updateRecipe(RecipeExtendedData recipe);
}

