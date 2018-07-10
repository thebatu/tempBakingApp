package com.example.bats.bakingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bats on 5/28/18.
 *
 */

public class Ingredient implements Parcelable{

    private Float quantity;
    private String measure;
    private String ingredient;

    public Ingredient(String quantity, String measure, String ingredient) {
        quantity = quantity;
        measure = measure;
        ingredient = ingredient;
    }


    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);

    }

    private Ingredient(Parcel in) {
        quantity = in.readFloat();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Parcelable.Creator<Ingredient> CREATOR
            = new Parcelable.Creator<Ingredient>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };



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
}
