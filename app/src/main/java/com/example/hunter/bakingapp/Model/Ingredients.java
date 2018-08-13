package com.example.hunter.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredients implements Parcelable {
    private String quantity;
    private String measure;
    private String ingredients;

    public Ingredients(String quantity, String measure, String ingredients) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredients = ingredients;
    }

    protected Ingredients(Parcel in) {
        quantity = in.readString();
        measure = in.readString();
        ingredients = in.readString();
    }

    public static final Creator<Ingredients> CREATOR = new Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel in) {
            return new Ingredients(in);
        }

        @Override
        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };

    public String getQuantity() {
        return quantity;
    }



    public String getMeasure() {
        return measure;
    }



    public String getIngredients() {
        return ingredients;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quantity);
        dest.writeString(measure);
        dest.writeString(ingredients);
    }
}
