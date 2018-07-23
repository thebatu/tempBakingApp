package com.example.bats.bakingapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.bats.bakingapp.Adapter.MainBakingAdapter;
import com.example.bats.bakingapp.Network.RecipeClient;
import com.example.bats.bakingapp.Models.Recipe;
import com.example.bats.bakingapp.R;
import com.example.bats.bakingapp.Utils.ApiError;
import com.example.bats.bakingapp.Utils.Constants;
import com.example.bats.bakingapp.Utils.ErrorUtils;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MainBakingAdapter.recipeClickListener{


    public static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Constants.baking_json)
            .addConverterFactory(GsonConverterFactory.create());

    public static Retrofit retrofit = builder.build();


    @BindView(R.id.progress_bar) ProgressBar progressBar;
    private MainBakingAdapter mAdapter;
    ArrayList<Recipe> recipes;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        progressBar.setVisibility(View.VISIBLE);
        this.context = this;

        RecyclerView mRecyclerView = findViewById(R.id.recipe_card_view);
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == 2) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        }


        if (orientation == 1){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(linearLayoutManager);
        }


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


        //Do the actual request
        RecipeClient recipeClient = retrofit.create(RecipeClient.class);
        Call<List<Recipe>> call = recipeClient.cookingRecpies(Constants.baking_json2);

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()){
                    recipes = (ArrayList<Recipe>) response.body();
                    mAdapter.setRecipeData(recipes);
                    mAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    ApiError apiError = ErrorUtils.parseError(response);
                    Toast.makeText(context, apiError.getMessage() , Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.i("", "ff" + t.getMessage());
                Toast.makeText(context, "Network Error " + t.getMessage(), Toast.LENGTH_LONG).show();
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
        Gson gson = new Gson();
        Intent intent = new Intent(this, DetailsRecipeActivity.class);
        intent.putExtra("recipe", gson.toJson(clickedOnRecipe));
        intent.putExtra("recipe_pos", clickedOnPos);
        startActivity(intent);

    }
}
