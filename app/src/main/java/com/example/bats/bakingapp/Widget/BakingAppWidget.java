package com.example.bats.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.bats.bakingapp.Activities.MainActivity;
import com.example.bats.bakingapp.Models.Ingredient;
import com.example.bats.bakingapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String recipeTitle, ArrayList ingredientList) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        views.setTextViewText(R.id.appwidget_text, recipeTitle);

        views.removeAllViews(R.id.container_w);
        if (ingredientList!=null){
            for (Ingredient ingredient : ingredientList) {
                RemoteViews ingredientView = new RemoteViews(context.getPackageName(),
                        R.layout.list_item_ingredient);
                ingredientView.setTextViewText(R.id.ingredient_name,ingredient.getIngredient());
                ingredientView.setTextViewText(R.id.ingredient_quantity,ingredient.getQuantity());
                ingredientView.setTextViewText(R.id.ingredient_measure,ingredient.getMeasure());
                views.addView(R.id.container_w, ingredientView);
            }
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        List<Ingredient> ingredients ;
        sharedPreferences = context.getSharedPreferences("preferences",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String result = sharedPreferences.getString("ingredients",null);
        Ingredient[] arrayIngredient = gson.fromJson(result,Ingredient[].class);
        ingredients = Arrays.asList(arrayIngredient);
        ingredients = new ArrayList<>(ingredients);
        String recipeName = sharedPreferences.getString("recipe_name",null);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeTitle, recipeSteps );
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

