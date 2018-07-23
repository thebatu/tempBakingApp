package com.example.bats.bakingapp.Fragments;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.bats.bakingapp.Adapter.RecyclerIngredientAdapter;
import com.example.bats.bakingapp.Models.Recipe;
import com.example.bats.bakingapp.R;
import com.example.bats.bakingapp.Widget.BakingAppWidgetProvider;
import com.example.bats.bakingapp.Widget.BakingAppWidgetService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsListFragment extends Fragment {

    @BindView(R.id.ingredients_recycler) RecyclerView ingredientRecyclerView;
   // private RecyclerIngredientAdapter ingredientRecyclerAdapter;
    @BindView(R.id.add_widget_btn) Button btn_widget;
    String recipeName;
    ArrayList ingredientsList;

    public IngredientsListFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ingredient_list_fragment, container, false);
        ButterKnife.bind(this, rootView);

        String string_recipe = getArguments().getString("recipe_string");
        Gson gson = new Gson();
        final Recipe recipe = gson.fromJson(string_recipe, Recipe.class);
        recipeName = recipe.getName();
        ingredientsList = recipe.getIngredients();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        ingredientRecyclerView.setLayoutManager(linearLayoutManager);
        ingredientRecyclerView.setHasFixedSize(true);
        RecyclerIngredientAdapter ingredientRecyclerAdapter = new RecyclerIngredientAdapter(getActivity(), recipe);
        ingredientRecyclerView.setAdapter(ingredientRecyclerAdapter);

        SharedPreferences sharedPreferences = getActivity()
                .getSharedPreferences("Shared_preferences", Context.MODE_PRIVATE);


        String ingredients = gson.toJson(recipe.getIngredients());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("recipe_ingredients",ingredients);
        editor.putString("recipe_name",recipe.getName());
        editor.putString("recipe", string_recipe);
        editor.apply();

        btn_widget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SharedPreferences sharedPreferences = getActivity()
//                        .getSharedPreferences("Shared_preferences", Context.MODE_PRIVATE);
//
//                Gson gson = new Gson();
//
//                String ingredients = gson.toJson(recipe.getIngredients());
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                editor.putString("recipe_ingredients",ingredients);
//                editor.putString("recipe_name",recipe.getName());
//                editor.apply();

//                ComponentName component=new ComponentName(getActivity(),BakingAppWidgetProvider.class);


                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity());

                Bundle bundle = new Bundle();
//
                int[] appWidgetIds = AppWidgetManager.getInstance(getActivity())
                        .getAppWidgetIds(new ComponentName(getActivity(), BakingAppWidgetProvider.class));






//                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.layout.list_item_ingredient);
//                BakingAppWidgetProvider.updateAppWidgets(getActivity(), appWidgetManager, appWidgetIds, recipeName,
//                        ingredientsList);

                Intent intent = new Intent(getActivity(), BakingAppWidgetProvider.class);
                intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
                int ids[] = AppWidgetManager.getInstance(getActivity()).getAppWidgetIds(new ComponentName(getActivity(), BakingAppWidgetService.class));
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                getActivity().sendBroadcast(intent);


//                Intent intent = new Intent(getActivity(), BakingAppWidgetService.class);
//                intent.setAction(BakingAppWidgetService.UPDATE_RECIPE_WIDGET);
//                getActivity().startService(intent);

                Toast.makeText(getActivity(), "Widget Updated", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

}
