package com.example.bats.bakingapp.Models;

/**
 * Created by bats on 5/28/18.
 *
 */

public class Ingredient {
    private Float quantity;

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    private String measure;
    private String ingredient;

}
