package fhooe.se.android.rezeptapp.DAL;

/**
 * Created by Lukas on 11.01.18.
 */

public class RecipeData {
    private int id;
    private String recipeName;
    private int icon;

    public RecipeData(){}
    RecipeData(String _recipeName, int _icon){
        this.recipeName  = _recipeName;
        this.icon = _icon;
    }

    RecipeData(int id, String _recipeName, int _icon){
        this.id = id;
        this.recipeName  = _recipeName;
        this.icon = _icon;
    }

    RecipeData(Recipe other){
        this.id = other.id;
        this.recipeName = other.recipeName;
        this.icon = other.icon;
    }

    protected void setId(int id){this.id = id;}

    public int getId(){return id;}
    public String getRecipeName(){return recipeName;}
    public int getIcon(){return icon;}
}
