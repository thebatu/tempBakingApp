package com.example.bats.bakingapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bats.bakingapp.Fragments.IngredientsStepsFragment;
import com.example.bats.bakingapp.Fragments.StepDetailFragment;
import com.example.bats.bakingapp.Models.Recipe;
import com.example.bats.bakingapp.Models.Steps;
import com.example.bats.bakingapp.R;
import com.google.gson.Gson;

public class DetailsStepsActivity extends AppCompatActivity implements StepDetailFragment.StepChangeClickListener  {

    String step_string;
    String recipe_string;
    int recipe_pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_steps);

        step_string = getIntent().getStringExtra("step");
        recipe_string = getIntent().getStringExtra("recipe");
        recipe_pos = getIntent().getIntExtra("position", 0);

        Bundle bundle = new Bundle();
        bundle.putString("step_string", step_string);
        bundle.putString("recipe_string", recipe_string);

        if (savedInstanceState == null) {
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setArguments(bundle);

            stepDetailFragment.setStepsList(recipe_string);
            stepDetailFragment.setStepData(recipe_pos);

            getSupportFragmentManager().beginTransaction().add(R.id.frag_steps, stepDetailFragment).commit();
        }

    }

    @Override
    public void stepChangeClickListener(int newPOS) {
        Toast.makeText(this, "In Details Activity  " + newPOS , Toast.LENGTH_LONG).show();
        int step = newPOS;

        Bundle bundle = new Bundle();
        bundle.putString("recipe_string", recipe_string);

        StepDetailFragment stepDetailFragment = new StepDetailFragment();
//        stepDetailFragment.setArrowClickListener(this);

        stepDetailFragment.setStepsList(recipe_string);
        stepDetailFragment.setStepData(step);

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_steps, stepDetailFragment).commit();
    }

}
