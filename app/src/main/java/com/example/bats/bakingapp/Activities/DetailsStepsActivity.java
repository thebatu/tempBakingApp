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

public class DetailsStepsActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_steps);


        String step_string = getIntent().getStringExtra("step");
        String recipe_string = getIntent().getStringExtra("recipe");

        Bundle bundle = new Bundle();
        bundle.putString("step_string", step_string);
        bundle.putString("recipe_string", recipe_string);

        Toast.makeText(this, "In Details Activity  " + step_string , Toast.LENGTH_LONG).show();

        if (savedInstanceState == null) {
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.frag_steps, stepDetailFragment).commit();
        }

    }
}
