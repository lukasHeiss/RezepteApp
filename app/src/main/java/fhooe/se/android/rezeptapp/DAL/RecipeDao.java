package fhooe.se.android.rezeptapp.DAL;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.webkit.WebIconDatabase;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Tom on 28.01.2018.
 */


@Dao
public interface RecipeDao{
    @Insert
    public long InsertRecipe(Recipe recipe);

    @Insert (onConflict = REPLACE)
    public void InsertInstructions(List<Instruction> instructions);

    @Insert (onConflict = REPLACE)
    public void InsertIngredients(List<Ingredient> ingredients);

    @Update
    public void UpdateRecipe(Recipe recipe);

    @Query("SELECT * FROM Recipe")
    public List<Recipe> LoadAllRecipes();

    @Query("SELECT * FROM Recipe Where id =:recipeId")
    public Recipe GetRecipeById(int recipeId);

    @Query("SELECT * FROM Recipe INNER JOIN Instruction WHERE recipeId = :recipeId")
    public List<Instruction> GetInstructions(int recipeId);

    @Query("SELECT * FROM Recipe INNER JOIN Ingredient WHERE recipeId = :recipeId")
    public List<Ingredient> GetIngredients(int recipeId);


}
