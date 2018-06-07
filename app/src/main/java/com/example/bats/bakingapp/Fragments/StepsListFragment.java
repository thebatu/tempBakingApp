package com.example.bats.bakingapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bats.bakingapp.Adapter.RecyclerStepsAdapter;
import com.example.bats.bakingapp.Models.Recipe;
import com.example.bats.bakingapp.Models.Steps;
import com.example.bats.bakingapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class StepsListFragment extends Fragment implements RecyclerStepsAdapter.StepOnclickListener {

    private static final String TAG = StepsListFragment.class.getSimpleName();

    RecyclerView stepsRecycelerView;
    RecyclerStepsAdapter recyclerStepsAdapter;
    private OnStepClickListener onStepClickListener;
    public Recipe recipe;

    public StepsListFragment(){

    }


    public interface OnStepClickListener {
        void onStepClickListener(Steps clickedOnStep, Recipe recipe);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onStepClickListener = (OnStepClickListener) context;
        }catch (Exception e) {
            Log.d(TAG, "onAttach Error in StepsListFragment: " + e.getMessage());

        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.steps_list_fragment, container, false);

        String string_recipe = getArguments().getString("recipe_string");
        Gson gson = new Gson();
        recipe = gson.fromJson(string_recipe, Recipe.class);

        stepsRecycelerView = rootView.findViewById(R.id.steps_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        stepsRecycelerView.setLayoutManager(linearLayoutManager);
        stepsRecycelerView.setHasFixedSize(true);

        recyclerStepsAdapter = new RecyclerStepsAdapter(getActivity(), recipe, this);
        stepsRecycelerView.setAdapter(recyclerStepsAdapter);

        return rootView;

    }

    @Override
    public void onClick(Steps clickedOnStep) {
        clickedOnStep.getId();
        Toast.makeText(getLayoutInflater().getContext(), "asdasd  " + clickedOnStep.getId(), Toast.LENGTH_LONG).show();

        onStepClickListener.onStepClickListener(clickedOnStep, recipe);
    }
}
