package com.example.bats.bakingapp.Activities;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.bats.bakingapp.Fragments.IngredientsListFragment;
import com.example.bats.bakingapp.Fragments.IngredientsStepsFragment;
import com.example.bats.bakingapp.Fragments.StepDetailFragment;
import com.example.bats.bakingapp.Fragments.StepsListFragment;
import com.example.bats.bakingapp.Models.Recipe;
import com.example.bats.bakingapp.Models.Steps;
import com.example.bats.bakingapp.R;
import com.google.gson.Gson;

public class DetailsRecipeActivity extends AppCompatActivity implements
        StepsListFragment.OnStepClickListener
{

    public final String TAG = DetailsStepsActivity.class.getSimpleName();
    private int screenSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_recipe);

        screenSize= getResources().getConfiguration().smallestScreenWidthDp;
        Gson gson = new Gson();
        String recipeString = getIntent().getStringExtra("recipe");
        Recipe recipe = gson.fromJson(recipeString, Recipe.class);
        Bundle bundle = new Bundle();
        bundle.putString("recipe_string", recipeString);


        if (savedInstanceState == null) {
            if (screenSize < 600) {

                IngredientsStepsFragment ingredientsStepsFragment = new IngredientsStepsFragment();
                ingredientsStepsFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.ingredients_steps_fragment, ingredientsStepsFragment).commit();

            } else {

                IngredientsListFragment ingredientsListFragment = new IngredientsListFragment();
                ingredientsListFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.recipe_ingredients_step_desc, ingredientsListFragment).commit();

                StepsListFragment stepsListFragment = new StepsListFragment();
                stepsListFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.recipe_step_instructions, stepsListFragment).commit();
            }
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    /**
     * click listener for steps
     * @param step  clicked on step
     * @param recipe    the recipe object
     */
    @Override
    public void onStepClickListener(Steps step, Recipe recipe, int position) {
        if (step != null) {
            Gson gson = new Gson();
            String step_toString = gson.toJson(step);
            String recipe_toString = gson.toJson(recipe);

            if (screenSize < 600) {

                Intent intent = new Intent(this, DetailsStepsActivity.class);
                intent.putExtra("step", step_toString);
                intent.putExtra("recipe", recipe_toString);
                intent.putExtra("position", position);
                startActivity(intent);

            } else {

                StepDetailFragment stepDetailFragment = new StepDetailFragment();
//                stepDetailFragment.setArrowClickListener(this);
                stepDetailFragment.setStepsList(recipe_toString);
                stepDetailFragment.setStepData(position);
                getSupportFragmentManager().beginTransaction().replace(R.id.video_explanation, stepDetailFragment).commit();
            }
        }
        else{
            Log.d(TAG, "Step is null " );
        }
    }

}
