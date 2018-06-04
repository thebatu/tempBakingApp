package com.example.bats.bakingapp.Models.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bats.bakingapp.Models.Recipe;
import com.example.bats.bakingapp.R;
import com.google.gson.Gson;

public class StepsListFragment extends Fragment {

    public StepsListFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.steps_list_fragment, container, false);

        String string_recipe = getArguments().getString("recipe_string");
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(string_recipe, Recipe.class);



    }
}
