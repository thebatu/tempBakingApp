package com.example.bats.bakingapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.bats.bakingapp.Fragments.IngredientsStepsFragment;
import com.example.bats.bakingapp.Fragments.StepsListFragment;
import com.example.bats.bakingapp.Models.Recipe;
import com.example.bats.bakingapp.Models.Steps;
import com.example.bats.bakingapp.R;
import com.google.gson.Gson;

public class DetailsRecipeActivity extends AppCompatActivity implements StepsListFragment.OnStepClickListener {

    public final String TAG = DetailsStepsActivity.class.getSimpleName();


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

    /**
     * click listener for steps
     * @param step  cliked on step
     * @param recipe    the recipe object
     */
    @Override
    public void onStepClickListener(Steps step, Recipe recipe) {
        if (step != null) {
            Gson gson = new Gson();
            String step_toString = gson.toJson(step);
            String recipe_toString = gson.toJson(recipe);

            Intent intent = new Intent(this, DetailsStepsActivity.class);
            intent.putExtra("step", step_toString);
            intent.putExtra("recipe", recipe_toString);
            startActivity(intent);
        }
        else{
            Log.d(TAG, "Step is null " );

        }

    }
}
