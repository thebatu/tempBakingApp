package com.example.bats.bakingapp.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import com.example.bats.bakingapp.Models.Ingredient;
import com.example.bats.bakingapp.R;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {
    SharedPreferences sharedPreferences;


    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String recipeTitle, List<Ingredient> ingredientList) {

        // Construct the RemoteViews object
        RemoteViews my_views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        my_views.setTextViewText(R.id.appwidget_title, recipeTitle);
        my_views.removeAllViews(R.id.widget_container);
        //my_views.removeAllViews(R.layout.list_item_ingredient);


        if (ingredientList!=null){
            for (Ingredient ingredient : ingredientList) {
                RemoteViews ingredientView = new RemoteViews(context.getPackageName(),
                        R.layout.list_item_ingredient);
                ingredientView.setTextViewText(R.id.ingredients_measure,ingredient.getMeasure());
                ingredientView.setTextViewText(R.id.ingredients_name,ingredient.getIngredient());
                ingredientView.setTextViewText(R.id.ingredients_quantity,String.valueOf(ingredient.getQuantity()));
                my_views.addView(R.id.widget_container, ingredientView);
            }
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, my_views);
    }


    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager,
                                 int[]appWidgetIds,String recipeTitle,
                                 List<Ingredient> ingredientList ){

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeTitle, ingredientList );
        }
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        List<Ingredient> ingredients ;
        sharedPreferences = context.getSharedPreferences("Shared_preferences",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String result = sharedPreferences.getString("recipe_ingredients",null);
        Ingredient[] arrayIngredient = gson.fromJson(result,Ingredient[].class);
        ingredients = Arrays.asList(arrayIngredient);
        ingredients = new ArrayList<>(ingredients);
        String recipeTitle = sharedPreferences.getString("recipe_name",null);
        for (int appWidgetId : appWidgetIds) {
//            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.layout.baking_app_widget);
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeTitle, ingredients );
        }


    }




}

