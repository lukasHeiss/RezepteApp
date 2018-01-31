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

import fhooe.se.android.rezeptapp.DAL.IDataManager;
import fhooe.se.android.rezeptapp.DAL.IngredientData;
import fhooe.se.android.rezeptapp.DAL.IngredientDataAdapter;
import fhooe.se.android.rezeptapp.DAL.InstructionDataAdapter;
import fhooe.se.android.rezeptapp.DAL.RecipeExtendedData;

public class edit_recipe extends Activity implements View.OnClickListener {
    private static IDataManager dataManager;
    ArrayAdapter adapter;
    RecipeExtendedData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);



        Button button = (Button)findViewById(R.id.activity_edit_recipe_addRecipe);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View _view) {
        EditText editText = (EditText) findViewById(R.id.activity_recipe_edit_text_recipename);
        String value = editText.getText().toString();


        RecipeExtendedData recipe = new RecipeExtendedData(value, R.drawable.img_placeholder);
        recipe.setDifficulty(R.string.diff_beginner);
        recipe.setTimePreparation(10);
        recipe.AddIngredient(new IngredientData(1, "Stück", "Tomate"));
        recipe.AddIngredient(new IngredientData(100, "g", "Teig"));
        recipe.AddInstruction("Die Tomaten auf den Teig legen!");
       // dataManager.saveRecipe(adapter, recipe);

        Toast.makeText(this,value, Toast.LENGTH_SHORT).show();

    }


    //helper function to create the Meta Views (Time, portions etc), to reduce double code.
    private View getMetaElementView(String data, int image){
        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_recipe_edit_meta, null);
        ImageView iv = (ImageView) view.findViewById(R.id.activity_recipe_meta_icon);
        iv.setImageResource(image);

        TextView tv = (TextView) view.findViewById(R.id.activity_recipe_edit_meta_text);

        return view;
    }


    public void dataLoaded() {

            //add meta elements
        LinearLayout metaElements = (LinearLayout) findViewById(R.id.activity_recipe_edit_metaList);
        metaElements.addView(getMetaElementView(String.valueOf(data.getTimeCooking()) + " m " + getResources().getString(R.string.cookTime), R.drawable.ic_clock));
        metaElements.addView(getMetaElementView(String.valueOf(data.getTimePreparation()) + " m " + getResources().getString(R.string.prepTime), R.drawable.ic_clock));
        metaElements.addView(getMetaElementView(getResources().getString(data.getDifficulty()), R.drawable.ic_chef));
        metaElements.addView(getMetaElementView(data.getBasePortions() + getResources().getString(R.string.servings), R.drawable.ic_cutlery));


        //add the List of instructions
        LinearLayout instructions = (LinearLayout) findViewById(R.id.activity_recipe_edit_instructionList);
        ArrayAdapter instructionAdapter = new InstructionDataAdapter(this, null);
        instructionAdapter.add("");
        for(int i = 0; i < instructionAdapter.getCount(); i++){
            instructions.addView(instructionAdapter.getView(i, null, instructions));
        }

        //add the List of ingredients
        LinearLayout ingredients = (LinearLayout) findViewById(R.id.activity_recipe_edit_ingredientList);
        ArrayAdapter ingredientsAdapter = new IngredientDataAdapter(this, null);
        ingredientsAdapter.add(new IngredientData(0, "", ""));
        for(int i = 0; i < ingredientsAdapter.getCount(); i++){
            ingredients.addView(ingredientsAdapter.getView(i, null, ingredients));
        }

    }
}
