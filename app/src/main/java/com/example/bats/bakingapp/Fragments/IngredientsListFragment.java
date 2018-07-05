package com.example.bats.bakingapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bats.bakingapp.Adapter.RecyclerIngredientAdapter;
import com.example.bats.bakingapp.Models.Recipe;
import com.example.bats.bakingapp.R;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsListFragment extends Fragment {


    @BindView(R.id.ingredients_recycler) RecyclerView ingredientRecyclerView;
    private RecyclerIngredientAdapter ingredientRecyclerAdapter;

    public IngredientsListFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ingredient_list_fragment, container, false);
        ButterKnife.bind(this, rootView);

        String string_recipe = getArguments().getString("recipe_string");
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(string_recipe, Recipe.class);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        ingredientRecyclerView.setLayoutManager(linearLayoutManager);
        ingredientRecyclerView.setHasFixedSize(true);

        ingredientRecyclerAdapter = new RecyclerIngredientAdapter(getActivity(), recipe);
        ingredientRecyclerView.setAdapter(ingredientRecyclerAdapter);

        return rootView;

    }


}
