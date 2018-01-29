package fhooe.se.android.rezeptapp.DAL;

import android.util.Log;

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
    private List<String> instructionList;
    private List<IngredientData> ingredientList;

    public RecipeExtendedData(String name, int icon) {
        super(name, icon);
        instructionList = new Vector<String>();
        ingredientList = new Vector<IngredientData>();
    }

    public RecipeExtendedData(Recipe other, List<String> instructions, List<IngredientData> ingredients) {
        super(other.id, other.recipeName, other.icon);
        instructionList = instructions;
        this.ingredientList = ingredients;
        Log.e("whatev", "instr:" + instructions.size() + "ingr:" + ingredients.size());
    }

    //getter
    public List<String> getInstructionList() {return  instructionList;}
    public List<IngredientData> getIngredientList() {return ingredientList;}
    public int getTimePreparation(){return timePreparation;}
    public int getTimeCooking(){return timeCooking;}
    public int getDifficulty(){return difficulty;}
    public int getBasePortions(){return basePortions;}

    //setter
    public void AddInstruction (String instruction) {instructionList.add(instruction);}
    public void AddIngredient (IngredientData ingredient){ingredientList.add(ingredient);}
    public void setTimePreparation(int time){timePreparation=time;}
    public void setTimeCooking(int time){timeCooking=time;}
    public void setDifficulty(int diff){this.difficulty=diff;}
    public void setBasePortions(int portions){this.basePortions=portions;}

    void setInstructionList(List<String> instructionList){this.instructionList = instructionList;}
    void setIngredientList(List<IngredientData> ingredientList) {this.ingredientList = ingredientList;}


}
