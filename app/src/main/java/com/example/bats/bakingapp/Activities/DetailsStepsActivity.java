package com.example.bats.bakingapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bats.bakingapp.Fragments.IngredientsStepsFragment;
import com.example.bats.bakingapp.Fragments.StepDetailFragment;
import com.example.bats.bakingapp.Models.Recipe;
import com.example.bats.bakingapp.Models.Steps;
import com.example.bats.bakingapp.R;
import com.google.gson.Gson;

public class DetailsStepsActivity extends AppCompatActivity implements StepDetailFragment.StepChangeClickListener  {

    private int mStepIndex;
    String step_string;
    String recipe_string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_steps);

        step_string = getIntent().getStringExtra("step");
        recipe_string = getIntent().getStringExtra("recipe");

        if (savedInstanceState == null) {
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setStepsList(recipe_string);
            getSupportFragmentManager().beginTransaction().add(R.id.frag_steps, stepDetailFragment).commit();
        }

    }

    @Override
    public void stepChangeClickListener(int newPOS) {
        Toast.makeText(this, "In Details Activity  " + newPOS , Toast.LENGTH_LONG).show();
        int step = mStepIndex + 1;
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setStepData(step);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_steps, stepDetailFragment).commit();
    }




}
