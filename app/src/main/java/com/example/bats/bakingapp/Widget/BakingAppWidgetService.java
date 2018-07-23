package com.example.bats.bakingapp.Widget;

import android.app.IntentService;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.bats.bakingapp.Activities.DetailsRecipeActivity;
import com.example.bats.bakingapp.Activities.DetailsStepsActivity;
import com.example.bats.bakingapp.Models.Ingredient;
import com.example.bats.bakingapp.Models.Recipe;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.bats.bakingapp.Widget.BakingAppWidgetProvider.updateAppWidget;

public class BakingAppWidgetService extends IntentService {


    public static final String SHOW_RECIPE_PAGE = "com.example.bats.bakingapp.action.display_recipe";
    public static final String UPDATE_RECIPE_WIDGET = "com.example.bats.bakingapp.action.update_recipe";

    static SharedPreferences sharedPreferences;
    static Intent my_intent = null;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public BakingAppWidgetService() {
        super("BakingAppWidgetService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (SHOW_RECIPE_PAGE.equals(action)) {


                String data = (String) intent.getExtras().get("recipe");
                Bundle bundle = new Bundle();
                bundle.putString("recipe", data);
                Intent intent1 = new Intent(getApplicationContext(), DetailsRecipeActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("recipe", data);
                startActivity(intent1);

//
//                JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("ingredientJSON"));
//                String recipeString = getIntent().getStringExtra("recipe");
//                Recipe recipe = gson.fromJson(recipeString, Recipe.class);

                //handleActionLunchRecipe();
            } else if (UPDATE_RECIPE_WIDGET.equals(action)) {
                handleActionUpdatePlantWidgets(getApplicationContext());
            }

        }
    }


    public static void startActionUpdateRecipeWidget(Context context) {
        Intent intent = new Intent(context, BakingAppWidgetService.class);
        intent.setAction(UPDATE_RECIPE_WIDGET);
        my_intent = intent;
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