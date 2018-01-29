package fhooe.se.android.rezeptapp;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import fhooe.se.android.rezeptapp.DAL.DALFactory;
import fhooe.se.android.rezeptapp.DAL.IDataManager;
import fhooe.se.android.rezeptapp.DAL.IngredientDataAdapter;
import fhooe.se.android.rezeptapp.DAL.InstructionDataAdapter;
import fhooe.se.android.rezeptapp.DAL.RecipeExtendedData;

public class RecipeActivity extends Activity implements RecipeDataCallBack{

    public static final String TAG="SingleRecipe";
    IDataManager manager = DALFactory.GetDataManager();
    RecipeExtendedData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        int recipeId = getIntent().getIntExtra("recipe", -1);
        Log.e(TAG, "recipe #" + recipeId + " selected");

        //get extended recipe. the callback function is called as soon as the recipe has been loaded.
        manager.GetRecipeExtended(recipeId, this);
    }

    //helper function to create the Meta Views (Time, portions etc), to reduce double code.
    private View getMetaElementView(String data, int image){
        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_recipe_meta, null);
        ImageView iv = (ImageView) view.findViewById(R.id.activity_recipe_meta_icon);
        iv.setImageResource(image);

        TextView tv = (TextView) view.findViewById(R.id.activity_recipe_meta_text);
        tv.setText(data);

        return view;
    }

    @Override
    public void dataLoaded(RecipeExtendedData recipe) {
        data = recipe;
        if (recipe != null){
            //add basic information
            TextView tv = (TextView) findViewById(R.id.activity_recipe_text_recipename);
            tv.setText(data.getRecipeName());

            ImageView iv = (ImageView) findViewById(R.id.activity_recipe_image);
            iv.setImageResource(data.getIcon());

            //add meta elements
            LinearLayout metaElements = (LinearLayout) findViewById(R.id.activity_recipe_metaList);
            if(data.getTimeCooking() != -1)
                metaElements.addView(getMetaElementView(String.valueOf(data.getTimeCooking()) + " m " + getResources().getString(R.string.cookTime), R.drawable.ic_clock));
            if(data.getTimePreparation() != -1)
                metaElements.addView(getMetaElementView(String.valueOf(data.getTimePreparation()) + " m " + getResources().getString(R.string.prepTime), R.drawable.ic_clock));
            if(data.getDifficulty() != -1)
                metaElements.addView(getMetaElementView(getResources().getString(data.getDifficulty()), R.drawable.ic_chef));
            metaElements.addView(getMetaElementView(data.getBasePortions()+"", R.drawable.ic_cutlery));


            //add the List of instructions
            LinearLayout instructions = (LinearLayout) findViewById(R.id.activity_recipe_instructionList);
            ArrayAdapter instructionAdapter = new InstructionDataAdapter(this, null);
            instructionAdapter.addAll(data.getInstructionList());
            for(int i = 0; i < instructionAdapter.getCount(); i++){
                instructions.addView(instructionAdapter.getView(i, null, instructions));
            }

            //add the List of ingredients
            LinearLayout ingredients = (LinearLayout) findViewById(R.id.activity_recipe_ingredientList);
            ArrayAdapter ingredientsAdapter = new IngredientDataAdapter(this, null);
            ingredientsAdapter.addAll(data.getIngredientList());
            for(int i = 0; i < ingredientsAdapter.getCount(); i++){
                ingredients.addView(ingredientsAdapter.getView(i, null, ingredients));
            }
        }
    }
}
