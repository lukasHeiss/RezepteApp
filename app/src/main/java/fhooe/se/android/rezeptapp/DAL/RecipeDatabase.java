package fhooe.se.android.rezeptapp.DAL;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.RoomDatabase;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Tom on 28.01.2018.
 * purely DAL-intern stuff.
 * Classes are package-private and should not be used outside of data access.
 */

@Database(entities = {Recipe.class, Instruction.class, Ingredient.class}, version = 1)
public abstract class RecipeDatabase extends RoomDatabase {
    public abstract RecipeDao recipeDao();
}



@Entity
class Recipe{
    @PrimaryKey (autoGenerate = true)
    protected int id;
    public String recipeName;
    public int icon;

    public int timePreparation;
    public int timeCooking;
    public int basePortions;
}

@Entity (primaryKeys = {"recipeId", "id"},
        foreignKeys = @ForeignKey(entity = Recipe.class,
        parentColumns = "id",
        childColumns = "recipeId",
        onDelete = CASCADE))
class Instruction{
    Instruction(int recipeId, int id, String instruction){
        this.recipeId = recipeId;
        this.id = id;
        this.instruction = instruction;
    }
    protected int recipeId;
    protected int id;

    public String instruction;
}

@Entity (primaryKeys = {"recipeId", "id"},
        foreignKeys = @ForeignKey(entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipeId",
        onDelete = CASCADE))
class Ingredient{
    Ingredient(int recipeId, int id, double amount, String unit, String ingredient){
        this.recipeId = recipeId;
        this.id = id;
        this.amount = amount;
        this.unit = unit;
        this.ingredient = ingredient;
    }
    Ingredient(int recipeId, int id, IngredientData other){
        this.recipeId = recipeId;
        this.id = id;
        this.amount = other.getAmount();
        this.unit = other.getUnit();
        this.ingredient = other.getIngredient();
    }
    protected int recipeId;
    protected int id;

    public double amount;
    public String unit;
    public String ingredient;
}



