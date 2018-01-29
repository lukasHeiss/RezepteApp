package fhooe.se.android.rezeptapp.DAL;

/**
 * Created by Tom on 24.01.2018.
 * Domain class for Ingredients. saves the amount, the used unit and the name of the ingredient.
 */

public class IngredientData {
    private double amount;
    private String unit;
    private String ingredient;

    public IngredientData(int amount, String unit, String ingredient) {
        this.amount = amount;
        this.unit = unit;
        this.ingredient = ingredient;
    }

    protected IngredientData(Ingredient other){
        this.amount = other.amount;
        this.unit = other.unit;
        this.ingredient = other.ingredient;
    }

    public void SetAmount(double amount) {this.amount = amount;}
    public void SetUnit(String unit) {this.unit = unit;}
    public void SetIngredient(String ingredient) {this.ingredient = ingredient;}

    public double getAmount() {return amount;}
    public String getUnit() {return unit;}
    public String getIngredient() {return ingredient;}
}
