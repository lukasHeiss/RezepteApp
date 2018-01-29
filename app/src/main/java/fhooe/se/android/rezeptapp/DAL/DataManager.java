package fhooe.se.android.rezeptapp.DAL;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import fhooe.se.android.rezeptapp.R;
import fhooe.se.android.rezeptapp.RecipeDataCallBack;

/**
 * Created by Tom on 11.01.2018.
 * Saves and manages All recipes.
 * Makes it easier to possibly later switch to a database driven solution
 */

public class DataManager extends Application implements IDataManager  {
    private boolean updating;
    private RecipeDatabase _db;
    private Context _context;
    private static int counter = 0;
    //private Map<Integer, RecipeExtendedData> recipes;

    @Override
    public void onCreate(){
        super.onCreate();
        _context = getApplicationContext();
        _db = Room.databaseBuilder(_context,
                RecipeDatabase.class, "recipe database").build();
        InitData();
    }

    void initDb(Context _context) {
        _db = Room.databaseBuilder(_context,
                RecipeDatabase.class, "recipe database").build();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void FillAdapter(final ArrayAdapter<Recipe> adapter, final Context context) {

        if(_db == null)initDb(context);
        final RecipeDao dao = _db.recipeDao();
        Log.e("whatev" , "filladapter started");

        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                while (updating) {
                    try{
                        Thread.sleep(5);
                    }catch (InterruptedException e){
                        Log.e("whatev", "interrupt! :O");
                    }
                }
                Log.e("whatev" , "filladapter running");

                adapter.addAll(dao.LoadAllRecipes());
                return null;
            }

            @Override
            protected void  onPostExecute(Integer bla){
                        adapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public void GetRecipeExtended(final int recipeId, final RecipeDataCallBack callBack) {
        //if(_db == null)initDb(context);
        final RecipeDao dao = _db.recipeDao();

        new AsyncTask<Void, Void, RecipeExtendedData>() {
            @Override
            protected RecipeExtendedData doInBackground(Void... params) {
                Log.e("whatev", "getExt: instr: "+ dao.GetInstructions(recipeId).size());

                List<String> instrList = new ArrayList<String>();
                List<IngredientData> ingrList = new ArrayList<IngredientData>();
                for (Instruction instr:dao.GetInstructions(recipeId))
                    instrList.add(instr.instruction);
                for(Ingredient ingr : dao.GetIngredients(recipeId))
                    ingrList.add(new IngredientData(ingr));
                RecipeExtendedData recipe = new RecipeExtendedData(dao.GetRecipeById(recipeId),
                        instrList, ingrList);
                return recipe;

            }

            @Override
            protected void onPostExecute(RecipeExtendedData recipe){
                callBack.dataLoaded(recipe);
            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void saveRecipe(final ArrayAdapter<Recipe> adapter, final RecipeExtendedData extendedRecipe) {
        final RecipeDao dao = _db.recipeDao();

        final Recipe recipe = new Recipe();
        recipe.id = extendedRecipe.getId();
        recipe.recipeName = extendedRecipe.getRecipeName();
        recipe.icon = extendedRecipe.getIcon();
        recipe.basePortions = extendedRecipe.getBasePortions();
        recipe.timeCooking = extendedRecipe.getTimeCooking();
        recipe.timePreparation = extendedRecipe.getTimePreparation();

        updating = true;
        new AsyncTask<Void, Void, Recipe>() {
            @Override
            protected Recipe doInBackground(Void... params) {
                long newId;
                try {
                    newId = dao.InsertRecipe(recipe);
                    recipe.id = (int)newId;
                }catch (SQLiteException e){
                    //double keys. try update instead.
                    dao.UpdateRecipe(recipe);
                    newId = recipe.id;
                }

                List<Instruction> instrList = new ArrayList<Instruction>();
                List<Ingredient> ingrList = new ArrayList<Ingredient>();

                int i = 0;
                for ( IngredientData ingr : extendedRecipe.getIngredientList())
                    ingrList.add(new Ingredient((int) newId, i++, ingr));
                dao.InsertIngredients(ingrList);

                i=0;
                for(String instr : extendedRecipe.getInstructionList())
                    instrList.add(new Instruction((int) newId, i++, instr));
                dao.InsertInstructions(instrList);
                return recipe;
            }

            @Override
            protected void onPostExecute(Recipe recipe){
                if(adapter != null){
                    adapter.add(recipe);
                    //adapter.notifyDataSetChanged();
                }
                else Log.e("whatev", "adapter null");
            }
        }.execute();
    }




    /*private void loadRecipes(final ArrayAdapter<Recipe> adapter){

        new AsyncTask<Void, Void, List<Recipe>>() {
            @Override
            protected List doInBackground(Void... params) {
                List<Recipe> result = new ArrayList<>();
                result = _db.recipeDao().LoadAllRecipes();
                return result;
            }

            @Override
            protected void onPostExecute(List result) {
                adapter.addAll(result);
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }*/




    private void InitData() {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
        boolean firstRun = p.getBoolean("PREFERENCE_FIRST_RUN", true);
        if(!firstRun) return;
        updating = true;

        RecipeExtendedData recipe = new RecipeExtendedData("Einfache Tomatensauce", R.drawable.img_placeholder);
        recipe.AddInstruction("Speck und Zwiebeln anbraten bis die Zwiebeln glasig sind, eventuell mit etwas Weißwein ablöschen.");
        recipe.AddInstruction( "Die Tomatenpampe beigeben, mit Paprikapulver, Salz & Pfeffer würzen. Ne Weile köcheln lassen.");
        //recipe.AddIngredient(new IngredientData);
        saveRecipe(null, recipe);

         recipe = new RecipeExtendedData("Spaghetti Carbonara", R.drawable.img_carbonara);
        recipe.AddInstruction("Erstmal die Spaghetti kochen");
        recipe.AddInstruction( "Währenddessen den Speck zusammen mit dem Knoblauch in etwas Öl anbraten.");
        recipe.AddInstruction( "Etwas Nudelwasser aufbehalten, die Nudeln zu Speck und Knoblauch in die Pfanne geben.\n" +
                "Die Eier und den Käse drübergeben, immer wieder gut rühren. Falls es zu trocken wird, etwas Nudelwasser zuschießen.");
        recipe.setTimeCooking(20);
        recipe.setTimePreparation(5);
        recipe.setDifficulty(R.string.diff_beginner);
        recipe.setBasePortions(2);


        recipe.AddIngredient(new IngredientData(1, "", "Knoblauch"));
        recipe.AddIngredient(new IngredientData(100, "g", "Speck"));
        recipe.AddIngredient(new IngredientData(2,"", "Eier"));
        saveRecipe(null, recipe);

        updating = false;

        p.edit().putBoolean("PREFERENCE_FIRST_RUN", false).apply();
    }

}
