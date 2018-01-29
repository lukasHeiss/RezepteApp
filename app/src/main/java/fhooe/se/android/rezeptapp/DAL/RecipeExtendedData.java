package fhooe.se.android.rezeptapp.DAL;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Lukas on 11.01.18.
 */

public class RecipeExtendedData extends RecipeData {
    private int timePreparation = -1;
    private int timeCooking = -1;
    private int difficulty = -1;
    private int basePortions = 2;
    private List<Instruction> instructionList;
    private List<Ingredient> ingredients;

    public RecipeExtendedData(String name, int icon) {
        super(name, icon);
        instructionList = new Vector<Instruction>();
        ingredients = new Vector<Ingredient>();
    }

    public RecipeExtendedData(Recipe other, List<Instruction> instructions, List<Ingredient> ingredients) {
        super(other.id, other.recipeName, other.icon);
        instructionList = instructions;
        this.ingredients = ingredients;
        Log.e("whatev", "instr:" + instructions.size() + "ingr:" + ingredients.size());
    }

    //getter
    public List<Instruction> getInstructionList() {return  instructionList;}
    public List<Ingredient> getIngredients() {return ingredients;}
    public int getTimePreparation(){return timePreparation;}
    public int getTimeCooking(){return timeCooking;}
    public int getDifficulty(){return difficulty;}
    public int getBasePortions(){return basePortions;}

    //setter
    public void AddInstruction (Instruction instruction) {instructionList.add(instruction);}
    public void AddIngredient (Ingredient ingredient){ingredients.add(ingredient);}
    public void setTimePreparation(int time){timePreparation=time;}
    public void setTimeCooking(int time){timeCooking=time;}
    public void setDifficulty(int diff){this.difficulty=diff;}
    public void setBasePortions(int portions){this.basePortions=portions;}

    void setInstructionList(List<Instruction> instructionList){this.instructionList = instructionList;}
    void setIngredientList(List<Ingredient> ingredientList) {this.ingredients = ingredientList;}


}
