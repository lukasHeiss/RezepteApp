package fhooe.se.android.rezeptapp.DAL;

import java.util.Vector;

/**
 * Created by Lukas on 11.01.18.
 */

public class RecipeExtendedData extends RecipeData {
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


}
