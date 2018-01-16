package fhooe.se.android.rezeptapp;

import android.os.Bundle;
import android.app.Activity;
import android.os.Debug;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import fhooe.se.android.rezeptapp.DAL.DALFactory;
import fhooe.se.android.rezeptapp.DAL.IDataManager;
import fhooe.se.android.rezeptapp.DAL.RecipeExtendedData;

public class RecipeActivity extends Activity {

    public static final String TAG="SingleRecipe";
    IDataManager manager = DALFactory.GetDataManager();
    RecipeExtendedData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        int recipeId = getIntent().getIntExtra("recipe", -1);
        Log.e(TAG, "recipe #" + recipeId + " selected");
        data = manager.GetRecipeExtended(recipeId);
        if(data != null) {
            TextView tv = (TextView) findViewById(R.id.activity_recipe_text_recipename);
            tv.setText(data.getRecipeName());

            ImageView iv = (ImageView) findViewById(R.id.activity_recipe_image);
            iv.setImageResource(data.getIcon());
        }
    }

}
