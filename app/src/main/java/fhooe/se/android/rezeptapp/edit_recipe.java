package fhooe.se.android.rezeptapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import fhooe.se.android.rezeptapp.DAL.DALFactory;
import fhooe.se.android.rezeptapp.DAL.IDataManager;
import fhooe.se.android.rezeptapp.DAL.IngredientData;
import fhooe.se.android.rezeptapp.DAL.IngredientDataAdapter;
import fhooe.se.android.rezeptapp.DAL.InstructionDataAdapter;
import fhooe.se.android.rezeptapp.DAL.RecipeExtendedData;

public class edit_recipe extends Activity implements View.OnClickListener, RecipeDataCallBack{
    private static IDataManager dataManager = DALFactory.GetDataManager();
    ArrayAdapter adapter;
    RecipeExtendedData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        int recipeId = getIntent().getIntExtra("recipe", 0);
        if(recipeId != 0)
            dataManager.GetRecipeExtended(recipeId, this);
        else {
            RecipeExtendedData recipe = new RecipeExtendedData("", R.drawable.img_placeholder);
            recipe.setTimePreparation(-1);
            recipe.setTimeCooking(-1);
            recipe.setDifficulty(-1);
            dataLoaded(recipe);
        }

        Button button = (Button)findViewById(R.id.activity_edit_recipe_addRecipe);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View _view) {
        EditText editText = (EditText) findViewById(R.id.activity_recipe_edit_text_recipename);
        data.setRecipeName(editText.getText().toString());

        data.setDifficulty(R.string.diff_beginner);
        data.setTimePreparation(10);
        data.AddIngredient(new IngredientData(1, "Stück", "Tomate"));
        data.AddIngredient(new IngredientData(100, "g", "Teig"));
        data.AddInstruction("Die Tomaten auf den Teig legen!");
        dataManager.saveRecipe(adapter, data);

        finish();

    }


    //helper function to create the Meta Views (Time, portions etc), to reduce double code.
    private View getMetaElementView(String data, int image){
        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_recipe_edit_meta, null);
        ImageView iv = (ImageView) view.findViewById(R.id.activity_recipe_meta_icon);
        iv.setImageResource(image);

        EditText tv = (EditText) view.findViewById(R.id.activity_recipe_edit_meta_text);

        return view;
    }

    @Override
    public void dataLoaded(RecipeExtendedData recipe) {
            //add meta elements
        data = recipe;

        LinearLayout metaElements = (LinearLayout) findViewById(R.id.activity_recipe_edit_metaList);
        metaElements.addView(getMetaElementView(String.valueOf(data.getTimeCooking()) + " m " + getResources().getString(R.string.cookTime), R.drawable.ic_clock));
        metaElements.addView(getMetaElementView(String.valueOf(data.getTimePreparation()) + " m " + getResources().getString(R.string.prepTime), R.drawable.ic_clock));
        //metaElements.addView(getMetaElementView(getResources().getString(data.getDifficulty()), R.drawable.ic_chef));
        metaElements.addView(getMetaElementView(data.getBasePortions() + getResources().getString(R.string.servings), R.drawable.ic_cutlery));


        //add the List of instructions
        LinearLayout instructions = (LinearLayout) findViewById(R.id.activity_recipe_edit_instructionList);
        ArrayAdapter instructionAdapter = new InstructionDataAdapter(this, null, true);
        instructionAdapter.add("");
        for(int i = 0; i < instructionAdapter.getCount(); i++){
            instructions.addView(instructionAdapter.getView(i, null, instructions));
        }

        //add the List of ingredients
        LinearLayout ingredients = (LinearLayout) findViewById(R.id.activity_recipe_edit_ingredientList);
        ArrayAdapter ingredientsAdapter = new IngredientDataAdapter(this, null, true);
        ingredientsAdapter.add(new IngredientData(0, "", ""));
        for(int i = 0; i < ingredientsAdapter.getCount(); i++){
            ingredients.addView(ingredientsAdapter.getView(i, null, ingredients));
        }

    }

}
