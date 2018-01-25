package fhooe.se.android.rezeptapp;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.app.Activity;
import android.os.Debug;
import android.support.v7.widget.RecyclerView;
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

            LinearLayout metaElements = (LinearLayout) findViewById(R.id.activity_recipe_metaList);
            if(data.getTimeCooking() != -1)
                metaElements.addView(getMetaElementView(String.valueOf(data.getTimeCooking()) + " m " + getResources().getString(R.string.cookTime), R.drawable.ic_clock));
            if(data.getTimePreparation() != -1)
                metaElements.addView(getMetaElementView(String.valueOf(data.getTimePreparation()) + " m " + getResources().getString(R.string.prepTime), R.drawable.ic_clock));
            if(data.getDifficulty() != -1)
                metaElements.addView(getMetaElementView(getResources().getString(data.getDifficulty()), R.drawable.ic_chef));
            metaElements.addView(getMetaElementView(data.getBasePortions()+"", R.drawable.ic_cutlery));


            LinearLayout instructions = (LinearLayout) findViewById(R.id.activity_recipe_instructionList);
            ArrayAdapter<String> instructionAdapter = new InstructionDataAdapter(this, null);
            instructionAdapter.addAll(data.getInstructionList());
            for(int i = 0; i < instructionAdapter.getCount(); i++){
                instructions.addView(instructionAdapter.getView(i, null, instructions));
            }

            LinearLayout ingredients = (LinearLayout) findViewById(R.id.activity_recipe_ingredientList);
            ArrayAdapter<IngredientData> ingredientsAdapter = new IngredientDataAdapter(this, null);
            ingredientsAdapter.addAll(data.getIngredients());
            for(int i = 0; i < ingredientsAdapter.getCount(); i++){
                ingredients.addView(ingredientsAdapter.getView(i, null, ingredients));
            }
        }
    }

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


    public static class ListUtils{
        public static void setDynamicHeight(ListView listView, Context context) {

            ListAdapter listAdapter = listView.getAdapter();
            if(listAdapter == null)
                return;
            int height= 0;
            int desiredWidth= View.MeasureSpec.makeMeasureSpec(screenWidth(context), View.MeasureSpec.UNSPECIFIED);
            for(int i=0; i< listAdapter.getCount(); i++){
                TextView listItem = (TextView) listAdapter.getView(i, null, listView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = height + (listView.getDividerHeight() * listAdapter.getCount() - 1);
            listView.setLayoutParams(params);
            listView.requestLayout();
        }

        private static int screenWidth(Context context) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Point size = new Point();
            wm.getDefaultDisplay().getSize(size);
            return size.x;
        }
    }

}
