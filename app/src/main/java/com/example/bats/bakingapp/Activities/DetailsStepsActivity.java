package com.example.bats.bakingapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.bats.bakingapp.Fragments.StepDetailFragment;
import com.example.bats.bakingapp.R;


/***
 * class that acts like master class to control stepDetailFragment where video playing is handled.
 */
public class DetailsStepsActivity extends AppCompatActivity implements StepDetailFragment.StepChangeClickListener  {

    String stepString;
    String recipeString;
    int recipePos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_steps);

        stepString = getIntent().getStringExtra("step");
        recipeString = getIntent().getStringExtra("recipe");
        recipePos = getIntent().getIntExtra("position", 0);

        Bundle bundle = new Bundle();
        bundle.putString("stepString", stepString);
        bundle.putString("recipe_string", recipeString);

        if (savedInstanceState == null) {
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setArguments(bundle);

            stepDetailFragment.setStepsList(recipeString);
            stepDetailFragment.setStepData(recipePos);

            getSupportFragmentManager().beginTransaction().add(R.id.frag_steps, stepDetailFragment).commit();
        }

    }


    //listener called when clicked on the next previous arrow buttons in exo player layout
    @Override
    public void stepChangeClickListener(int newPOS) {

        Bundle bundle = new Bundle();
        bundle.putString("recipe_string", recipeString);

        StepDetailFragment stepDetailFragment = new StepDetailFragment();

        stepDetailFragment.setStepsList(recipeString);
        stepDetailFragment.setStepData(newPOS);

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_steps, stepDetailFragment).commit();
    }

}
