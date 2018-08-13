package com.example.hunter.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {
    private int recipe_id;
    private String recipe_name;
//    private String recipe_ingredients;
//    private String recipe_steps;
    public ArrayList<Ingredients> ingredients;
    public ArrayList<Steps> steps;
    private int servings;
    public String image;

    public Recipe(int recipe_id, String recipe_name, ArrayList<Ingredients> ingredients, ArrayList<Steps> steps, int servings, String image) {
        this.recipe_id = recipe_id;
        this.recipe_name = recipe_name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    protected Recipe(Parcel in) {
        recipe_id = in.readInt();
        recipe_name = in.readString();
        ingredients = in.createTypedArrayList(Ingredients.CREATOR);
        steps = in.createTypedArrayList(Steps.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override

        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getRecipe_id() {
        return recipe_id;
    }

//    public void setRecipe_id(int recipe_id) {
//        this.recipe_id = recipe_id;
//    }

    public String getRecipe_name() {
        return recipe_name;
    }

//    public void setRecipe_name(String recipe_name) {
//        this.recipe_name = recipe_name;
//    }

    public ArrayList<Ingredients> getIngredients() {
        return ingredients;
    }

//    public void setIngredients(ArrayList<Ingredients> ingredients) {
//        this.ingredients = ingredients;
//    }

    public ArrayList<Steps> getSteps() {
        return steps;
    }

//    public void setSteps(ArrayList<Steps> steps) {
//        this.steps = steps;
//    }

//    public int getServings() {
//        return servings;
//    }

//    public void setServings(int servings) {
//        this.servings = servings;
//    }

//    public String getImage() {
//        return image;
//    }

//    public void setImage(String image) {
//        this.image = image;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recipe_id);
        dest.writeString(recipe_name);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
        dest.writeInt(servings);
        dest.writeString(image);
    }
}

