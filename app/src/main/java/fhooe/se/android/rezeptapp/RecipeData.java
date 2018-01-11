package fhooe.se.android.rezeptapp;

/**
 * Created by Lukas on 11.01.18.
 */

public class RecipeData {
    private int id;
    private String recipeName;
    private int icon;

    public RecipeData(){}

    public RecipeData(int _id, String _recipeName, int _icon){
        id = _id;
        recipeName  = _recipeName;
        icon = _icon;
    }

    public int getId(){return id;}
    public String getRecipeName(){return recipeName;}
    public int getIcon(){return icon;}
}
