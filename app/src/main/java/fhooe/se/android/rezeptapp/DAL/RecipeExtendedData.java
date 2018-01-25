package fhooe.se.android.rezeptapp.DAL;

import java.util.Vector;

/**
 * Created by Lukas on 11.01.18.
 */

public class RecipeExtendedData extends RecipeData {
    private int timePreparation = -1;
    private int timeCooking = -1;
    private int difficulty = -1;
    private int basePortions = 2;
    private Vector<String> instructionList;
    private Vector<IngredientData> ingredients;

    public RecipeExtendedData(int id, String name, int icon) {
        super(id, name, icon);
        instructionList = new Vector<String>();
        ingredients = new Vector<IngredientData>();
    }

    public Vector<String> getInstructionList() {return  instructionList;}
    public void AddInstruction (String instruction) {instructionList.add(instruction);}
    public Vector<IngredientData> getIngredients() {return ingredients;}
    public void AddIngredient (IngredientData ingredient){ingredients.add(ingredient);}
    public int getTimePreparation(){return timePreparation;}
    public void setTimePreparation(int time){timePreparation=time;}
    public int getTimeCooking(){return timeCooking;}
    public void setTimeCooking(int time){timeCooking=time;}
    public int getDifficulty(){return difficulty;}
    public void setDifficulty(int diff){this.difficulty=diff;}
    public int getBasePortions(){return basePortions;}
    public void setBasePortions(int portions){this.basePortions=portions;}


}
