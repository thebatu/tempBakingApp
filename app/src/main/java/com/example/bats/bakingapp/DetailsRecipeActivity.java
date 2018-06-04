package com.example.bats.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bats.bakingapp.Models.Fragments.IngredientsStepsFragment;
import com.example.bats.bakingapp.Models.Recipe;
import com.google.gson.Gson;

public class DetailsRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_recipe);

        Gson gson = new Gson();
        String recipeString = getIntent().getStringExtra("recipe");
        Recipe recipe = gson.fromJson(recipeString, Recipe.class);

        Bundle bundle = new Bundle();
        bundle.putString("recipe_string", recipeString);

        IngredientsStepsFragment ingredientsStepsFragment = new IngredientsStepsFragment();
        ingredientsStepsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.ingredients_steps_fragment, ingredientsStepsFragment).commit();


    }
}
