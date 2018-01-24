package fhooe.se.android.rezeptapp.DAL;

/**
 * Created by Tom on 24.01.2018.
 */

public class IngredientData {
    private int amount;
    private int unit;
    private String ingredient;

    public IngredientData(int amount, int unit, String ingredient) {
        this.amount = amount;
        this.unit = unit;
        this.ingredient = ingredient;
    }

    public void SetAmount(int amount) {this.amount = amount;}
    public void SetUnit(int unit) {this.unit = unit;}
    public void SetIngredient(String ingredient) {this.ingredient = ingredient;}

    public int getAmount() {return amount;}
    public int getUnit() {return unit;}
    public String getIngredient() {return ingredient;}
}
