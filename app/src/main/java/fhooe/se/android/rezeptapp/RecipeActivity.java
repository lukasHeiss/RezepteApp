package fhooe.se.android.rezeptapp;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

import fhooe.se.android.rezeptapp.DAL.DALFactory;
import fhooe.se.android.rezeptapp.DAL.IDataManager;
import fhooe.se.android.rezeptapp.DAL.IngredientData;
import fhooe.se.android.rezeptapp.DAL.IngredientDataAdapter;
import fhooe.se.android.rezeptapp.DAL.InstructionDataAdapter;
import fhooe.se.android.rezeptapp.DAL.RecipeDataAdapter;
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

            ListView instructions = (ListView) findViewById(R.id.activity_recipe_instructionList);
            ArrayAdapter<String> instructionAdapter = new InstructionDataAdapter(this, null);
            instructionAdapter.addAll(data.getInstructionList());
            instructions.setAdapter(instructionAdapter);
            //ListUtils.setDynamicHeight(instructions);

            ListView ingredients = (ListView) findViewById(R.id.activity_recipe_ingredientList);
            ArrayAdapter<IngredientData> ingredientAdapter = new IngredientDataAdapter(this, null);
            ingredientAdapter.addAll(data.getIngredients());
            ingredients.setAdapter(ingredientAdapter);
            //ListUtils.setDynamicHeight(ingredients);
        }
    }

    public static class ListUtils{
        public static void setDynamicHeight(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if(listAdapter == null)
                return;
            int height= 0;
            int desiredWidth= View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for(int i=0; i< listAdapter.getCount(); i++){
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = height + (listView.getDividerHeight() * listAdapter.getCount() - 1);
            listView.setLayoutParams(params);
            listView.requestLayout();
        }
    }

}
