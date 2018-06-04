package com.example.bats.bakingapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bats.bakingapp.Models.Recipe;
import com.example.bats.bakingapp.Models.Ingredient;
import com.example.bats.bakingapp.R;
import java.util.ArrayList;

public class RecyclerIngredientAdapter extends RecyclerView.Adapter<RecyclerIngredientAdapter.IngredientsViewHolder>{

    Context mContext;
    Recipe recipe;
    ArrayList<Ingredient> ingredients;


    public RecyclerIngredientAdapter(Context context, Recipe recipe) {
        this.mContext = context;
        this.recipe = recipe;
        this.ingredients = recipe.getIngredients();
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_ingredient, parent, false);
        return new IngredientsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerIngredientAdapter.IngredientsViewHolder holder, int position) {

        Ingredient current_ingredient = ingredients.get(position);
        holder.ingredients_name.setText(current_ingredient.getIngredient());
        holder.ingredients_quantity.setText(String.valueOf(current_ingredient.getQuantity()));
        holder.ingredients_measure.setText(current_ingredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        if (ingredients == null) {return 0;}
        return ingredients.size();
    }


    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        public TextView ingredients_name;
        public TextView ingredients_quantity;
        public TextView ingredients_measure;

        public IngredientsViewHolder(View itemView) {
            super(itemView);
            ingredients_name = itemView.findViewById(R.id.ingredients_name);
            ingredients_quantity = itemView.findViewById(R.id.ingredients_quantity);
            ingredients_measure = itemView.findViewById(R.id.ingredients_measure);

        }
    }
}
