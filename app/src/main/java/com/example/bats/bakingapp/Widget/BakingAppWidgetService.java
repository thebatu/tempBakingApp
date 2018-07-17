package com.example.bats.bakingapp.Widget;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class BakingAppWidgetService extends IntentService{

    public static final String SHOW_RECIPE_PAGE = "com.example.bats.bakingapp.action.display_recipe";



    public BakingAppWidgetService() {
        super("BakingAppWidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (SHOW_RECIPE_PAGE.equals(action)) {
                handleLunchRecipe();
            }

        }
    }

    private void handleLunchRecipe() {

    }
}
