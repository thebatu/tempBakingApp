package com.example.bats.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.bats.bakingapp.Adapter.MyAdapter;
import com.example.bats.bakingapp.Models.Network.RecipeClient;
import com.example.bats.bakingapp.Models.Recipe;
import com.example.bats.bakingapp.Utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recipe_card_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        createRetrofitBuilder();

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(recipes);
        mRecyclerView.setAdapter(mAdapter);


    }

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
                Toast.makeText(MainActivity.this, "Success" + response.body(), Toast.LENGTH_LONG).show();
                recipes = response.body();
                Log.i("", "ff" + recipes);


            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("", "ff" + t.getMessage());
            }
        });
    }


}
