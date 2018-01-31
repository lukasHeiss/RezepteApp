package fhooe.se.android.rezeptapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import fhooe.se.android.rezeptapp.DAL.DALFactory;
import fhooe.se.android.rezeptapp.DAL.IDataManager;
import fhooe.se.android.rezeptapp.DAL.IngredientData;
import fhooe.se.android.rezeptapp.DAL.IngredientDataAdapter;
import fhooe.se.android.rezeptapp.DAL.InstructionDataAdapter;
import fhooe.se.android.rezeptapp.DAL.RecipeExtendedData;

public class edit_recipe extends Activity implements View.OnClickListener, RecipeDataCallBack, TextView.OnEditorActionListener{
    private static IDataManager dataManager = DALFactory.GetDataManager();
    InstructionDataAdapter instrAdapter;
    IngredientDataAdapter ingrAdapter;
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

        Spinner spinner = (Spinner) findViewById(R.id.activity_recipe_edit_difficulty);
        if(spinner.getSelectedItem().equals(getResources().getString(R.string.diff_beginner)))
            data.setDifficulty(R.string.diff_beginner);
        if(spinner.getSelectedItem().equals(getResources().getString(R.string.diff_advanced)))
            data.setDifficulty(R.string.diff_advanced);
        if(spinner.getSelectedItem().equals(getResources().getString(R.string.diff_master)))
            data.setDifficulty(R.string.diff_master);

        LinearLayout metaList = (LinearLayout)findViewById(R.id.activity_recipe_edit_metaList);

        data.setTimeCooking(GetIntFromEditText((EditText) metaList.getChildAt(0).findViewById(R.id.activity_recipe_edit_meta_text)));
        data.setTimePreparation(GetIntFromEditText((EditText)metaList.getChildAt(1).findViewById(R.id.activity_recipe_edit_meta_text)));
        data.setBasePortions(GetIntFromEditText((EditText)metaList.getChildAt(2).findViewById(R.id.activity_recipe_edit_meta_text)));


        LinearLayout instructions = (LinearLayout) findViewById(R.id.activity_recipe_edit_instructionList);
        for(int i = 1; i < instructions.getChildCount(); i++){
            data.AddInstruction(((EditText)instructions.getChildAt(i).findViewById(R.id.activity_recipe_edit_instructionelement_text)).getText().toString());
        }

        LinearLayout ingredients = (LinearLayout) findViewById(R.id.activity_recipe_edit_ingredientList);
        for(int i = 2; i < ingredients.getChildCount(); i++){
            String ingredient = ((EditText)ingredients.getChildAt(i).findViewById(R.id.activity_recipe_edit_ingredientelement_text)).getText().toString();
            double amount = GetIntFromEditText((EditText)ingredients.getChildAt(i).findViewById(R.id.activity_recipe_edit_ingredientelement_number)) / data.getBasePortions();
            String unit = ((EditText)ingredients.getChildAt(i).findViewById(R.id.activity_recipe_edit_ingredientelement_unit)).getText().toString();
            data.AddIngredient(new IngredientData(amount, unit, ingredient));
        }

        dataManager.saveRecipe(adapter, data);

        finish();

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        Log.e("whatev", "onEditorAction actionId:" + actionId + " event:" + event);
        if(actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE  ||
                event != null &&
                event.getAction() == KeyEvent.ACTION_DOWN &&
                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            if(event == null || !event.isShiftPressed()){
                Log.e("whatev", "hmm" + v);
                if(v == findViewById(R.id.activity_recipe_edit_ingredientelement_text)){
                    AddIngredientField();
                }
                else if(v == findViewById(R.id.activity_recipe_edit_instructionelement_text))
                    AddInstructionField();
                return true;
            }
        }
        return false;
    }

    private int GetIntFromEditText(EditText editText) {
        try{
            return Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException e){
            return 0;
        }
    }

    private void AddIngredientField() {
        Log.e("whatev", "Add ingrfield");
        LinearLayout ingredients = (LinearLayout) findViewById(R.id.activity_recipe_edit_ingredientList);

        View view = ingrAdapter.getView(0, null, ingredients);
        ((EditText)view.findViewById(R.id.activity_recipe_edit_ingredientelement_text)).setOnEditorActionListener(this);
        ingredients.addView(view);
    }

    private void AddInstructionField() {
        LinearLayout instructions = (LinearLayout) findViewById(R.id.activity_recipe_edit_instructionList);

        View view = instrAdapter.getView(0, null, instructions);
        ((EditText)view.findViewById(R.id.activity_recipe_edit_instructionelement_text)).setOnEditorActionListener(this);
        instructions.addView(view);
    }


    //helper function to create the Meta Views (Time, portions etc), to reduce double code.
    private View getMetaElementView(int data, int image, int hint){
        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_recipe_edit_meta, null);
        ImageView iv = (ImageView) view.findViewById(R.id.activity_recipe_meta_icon);
        iv.setImageResource(image);

        EditText tv = (EditText) view.findViewById(R.id.activity_recipe_edit_meta_text);
        tv.setHint(getResources().getString(hint));
        if(data > 0)
            tv.setText(data +"");

        return view;
    }

    @Override
    public void dataLoaded(RecipeExtendedData recipe) {
            //add meta elements
        data = recipe;

        TextView tv = (TextView) findViewById(R.id.activity_recipe_edit_text_recipename);
        tv.setText(data.getRecipeName());

        LinearLayout metaElements = (LinearLayout) findViewById(R.id.activity_recipe_edit_metaList);
        metaElements.addView(getMetaElementView(data.getTimeCooking() , R.drawable.ic_clock, R.string.cookTime));
        metaElements.addView(getMetaElementView(data.getTimePreparation(), R.drawable.ic_clock, R.string.prepTime));

        ImageView iv = (ImageView) findViewById(R.id.activity_recipe_diff_icon);
        iv.setImageResource(R.drawable.ic_chef);

        Spinner spinner = (Spinner) findViewById(R.id.activity_recipe_edit_difficulty);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item);
        adapter.addAll(getResources().getStringArray(R.array.difficulties));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if(data.getDifficulty() > 0){
            int i = 0;
            for(String str : getResources().getStringArray(R.array.difficulties)){
                if(str.equals(getResources().getString(data.getDifficulty())))
                    spinner.setSelection(i);
                i++;
            }
        }
        metaElements.addView(getMetaElementView(data.getBasePortions(), R.drawable.ic_cutlery, R.string.servings));


        //add the List of instructions
        LinearLayout instructions = (LinearLayout) findViewById(R.id.activity_recipe_edit_instructionList);
        instrAdapter = new InstructionDataAdapter(this, null, true);
        instrAdapter.add("");
        for(int i = 0; i < instrAdapter.getCount(); i++){
            View view = instrAdapter.getView(i, null, instructions);
            ((EditText)view.findViewById(R.id.activity_recipe_edit_instructionelement_text)).setOnEditorActionListener(this);
            instructions.addView(view);
        }

        //add the List of ingredients
        LinearLayout ingredients = (LinearLayout) findViewById(R.id.activity_recipe_edit_ingredientList);
        ingrAdapter = new IngredientDataAdapter(this, null, true);
        ingrAdapter.add(new IngredientData(0, "", ""));
        for(int i = 0; i < ingrAdapter.getCount(); i++){
            View view = ingrAdapter.getView(i, null, ingredients);
            ((EditText)view.findViewById(R.id.activity_recipe_edit_ingredientelement_text)).setOnEditorActionListener(this);
            ingredients.addView(view);
        }

    }

}
