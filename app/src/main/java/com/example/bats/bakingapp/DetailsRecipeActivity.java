package com.example.bats.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.bats.bakingapp.Models.Fragments.IngredientsStepsFragment;
import com.example.bats.bakingapp.Models.Recipe;
import com.google.gson.Gson;

public class DetailsRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_recipe);

        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("recipe");
        Recipe recipe = gson.fromJson(strObj, Recipe.class);

        IngredientsStepsFragment ingredientsStepsFragment = new IngredientsStepsFragment();
//        ingredientsStepsFragment.setRecipeId(id);
//        ingredientsStepsFragment.setRecipeName(name);
        getSupportFragmentManager().beginTransaction().add(R.id.ingredients_steps_fragment, ingredientsStepsFragment).commit();


    }
}
