package com.example.bats.bakingapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.example.bats.bakingapp.Adapter.MainBakingAdapter;
import com.example.bats.bakingapp.Network.RecipeClient;
import com.example.bats.bakingapp.Models.Recipe;
import com.example.bats.bakingapp.R;
import com.example.bats.bakingapp.Utils.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MainBakingAdapter.recipeClickListener{

    private RecyclerView mRecyclerView;
    private MainBakingAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Recipe> recipes;
    Context context;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = this;

        mRecyclerView = findViewById(R.id.recipe_card_view);

        // use a layout manager
        //mLayoutManager = new LinearLayoutManager(this);
        //GridLayoutManager gridLayout  = new GridLayoutManager(context, 3);
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, 1);

        mRecyclerView.setLayoutManager(gaggeredGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        // specify an adapter (see also next example)
        mAdapter = new MainBakingAdapter(context, this );
        mRecyclerView.setAdapter(mAdapter);

        createRetrofitBuilder();

    }

    /**
     * create a retofit builder to fetch the main Json from the net
     */
    private void createRetrofitBuilder() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.baking_json)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        //Do the actual request
        RecipeClient recipeClient = retrofit.create(RecipeClient.class);
        Call<List<Recipe>> call = recipeClient.cookingRecpies(Constants.baking_json2);

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                //Toast.makeText(MainActivity.this, "Success" + response.body(), Toast.LENGTH_LONG).show();
                recipes = (ArrayList<Recipe>) response.body();
                mAdapter.setRecipeData(recipes);
                mAdapter.notifyDataSetChanged();
                Log.i("", "ff" + recipes);

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
               // Toast.makeText(MainActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("", "ff" + t.getMessage());
            }
        });
    }


    /**
     * callback for MainBakingAdapter on a recipe click
     * @param clickedOnPos  position of the card
     * @param clickedOnRecipe   the recipe object that was clicked on.
     */
    @Override
    public void onRecipeCardClick(int clickedOnPos, Recipe clickedOnRecipe) {
        //Toast.makeText(MainActivity.this, " " + clickedOnRecipe.getName(), Toast.LENGTH_LONG).show();
        Gson gson = new Gson();
        Intent intent = new Intent(this, DetailsRecipeActivity.class);
        intent.putExtra("recipe", gson.toJson(clickedOnRecipe));
        startActivity(intent);

    }
}
