package com.example.bats.bakingapp.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.bats.bakingapp.Models.Ingredient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.bats.bakingapp.Widget.BakingAppWidgetProvider.updateAppWidget;

public class BakingAppWidgetService extends IntentService {


    public static final String SHOW_RECIPE_PAGE = "com.example.bats.bakingapp.action.display_recipe";
    public static final String UPDATE_RECIPE_WIDGET = "com.example.bats.bakingapp.action.display_recipe";

    static SharedPreferences sharedPreferences;


    public BakingAppWidgetService() {
        super("BakingAppWidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (SHOW_RECIPE_PAGE.equals(action)) {
                handleActionLunchRecipe();
            } else if (UPDATE_RECIPE_WIDGET.equals(action)) {
                handleActionUpdatePlantWidgets(getApplicationContext());
            }

        }
    }



    private void handleActionLunchRecipe() {

    }

    public static void startActionUpdateRecipeWidget(Context context) {
        Intent intent = new Intent(context, BakingAppWidgetService.class);
        intent.setAction(UPDATE_RECIPE_WIDGET);
        context.startService(intent);
    }


    public void handleActionUpdatePlantWidgets(Context context) {

        List<Ingredient> ingredients;
        sharedPreferences = context.getSharedPreferences("Shared_preferences",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String result = sharedPreferences.getString("recipe_ingredients", null);
        Ingredient[] arrayIngredient = gson.fromJson(result, Ingredient[].class);
        ingredients = Arrays.asList(arrayIngredient);
        ingredients = new ArrayList<>(ingredients);
        String recipeTitle = sharedPreferences.getString("recipe_name", null);


        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);


        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, BakingAppWidgetProvider.class));

        Log.e("Ids", "Ids" + Arrays.toString(appWidgetIds));

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeTitle, ingredients);
        }

    }
}