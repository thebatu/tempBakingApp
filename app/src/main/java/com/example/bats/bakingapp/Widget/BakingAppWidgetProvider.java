package com.example.bats.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.bats.bakingapp.Models.Ingredient;
import com.example.bats.bakingapp.R;
import com.google.gson.Gson;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {

    static SharedPreferences sharedPreferences;


    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId, String recipeTitle, List<Ingredient> ingredientList) {

        // Construct the RemoteViews object
        RemoteViews my_views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        my_views.setTextViewText(R.id.appwidget_title, recipeTitle);
        my_views.removeAllViews(R.id.widget_container);
        //my_views.removeAllViews(R.layout.list_item_ingredient);

        sharedPreferences = context.getSharedPreferences("Shared_preferences",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();

        String recipe = sharedPreferences.getString("recipe", null);


        Intent intent = new Intent(context, BakingAppWidgetService.class);
        intent.setAction(BakingAppWidgetService.SHOW_RECIPE_PAGE);
        String ingredientJSON = gson.toJson(ingredientList);
        intent.putExtra("ingredientList",ingredientJSON);
        intent.putExtra("recipe",recipe);

        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT);
        // Get the layout for the App Widget and attach an on-click listener to the button
        my_views.setOnClickPendingIntent(R.id.my_widget_main, pendingIntent);


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


        BakingAppWidgetService.startActionUpdateRecipeWidget(context);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Toast.makeText( context, "on recieve Provider", Toast.LENGTH_SHORT).show();
        BakingAppWidgetService.startActionUpdateRecipeWidget(context);

    }




}

