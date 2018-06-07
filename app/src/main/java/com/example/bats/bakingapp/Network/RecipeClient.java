package com.example.bats.bakingapp.Network;

import com.example.bats.bakingapp.Models.Recipe;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by bats on 5/28/18.
 *
 */

public interface RecipeClient {

    @GET("{url}")
    Call<List<Recipe>> cookingRecpies(@Path("url") String url);


}
