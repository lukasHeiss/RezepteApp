package fhooe.se.android.rezeptapp;

import fhooe.se.android.rezeptapp.DAL.RecipeExtendedData;

/**
 * Created by Tom on 29.01.2018.
 */

public interface RecipeDataCallBack {
    void dataLoaded(RecipeExtendedData recipe);
}
