package com.example.bats.bakingapp.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bats.bakingapp.Models.Recipe;
import com.example.bats.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by bats on 5/28/18.
 * Recycler view for the main Activity which fetches and displays main baking recipes cards.
 *
 */

public class MainBakingAdapter extends RecyclerView.Adapter<MainBakingAdapter.MainBaking> {

    private List<Recipe> mRecipe;
    Context mContext;
    private final recipeClickListener mRecipeClickLis;
    public MainBakingAdapter (Context context, recipeClickListener listener) {
        mRecipeClickLis = listener;
        mContext = context;
    }

    public interface recipeClickListener{
        void onRecipeCardClick (int clickedOnPos, Recipe clickedOnRecipe);
    }

    public MainBaking onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context mContext = viewGroup.getContext();
        int my_card_layout = R.layout.my_card_layout;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(my_card_layout, viewGroup, false);
        MainBaking viewHolder = new MainBaking(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(MainBaking holder, int position) {
        Recipe recipe = mRecipe.get(position);
        holder.itemTitle.setText(recipe.getName());
        String hold = String.valueOf(recipe.getServings()) + " " + mContext.getString(R.string.servings);
        holder.itemServings.setText(hold);

        //Load image if exists otherwise load a place holder
        if (!recipe.getImage().isEmpty()){
            Picasso.get().load(recipe.getImage()).into(holder.itemImage);
        }else{
            switch (recipe.getId()){
                case 1:
                    holder.itemImage.setImageResource(R.drawable.nutellapie3);
                    break;
                case 2:
                    holder.itemImage.setImageResource(R.drawable.brownies3);
                    break;
                case 3:
                    holder.itemImage.setImageResource(R.drawable.yellowcake2);
                    break;
                case 4:
                    holder.itemImage.setImageResource(R.drawable.cheesecake);
                    break;
                default:
                    holder.itemImage.setImageResource(R.drawable.cutting);
                    break;
            }
        }
    }

    public void setRecipeData(List recipe) {
        if (recipe != null) {
            mRecipe = recipe;
        }
    }

    @Override
    public int getItemCount() {
        if (mRecipe != null) {
            return mRecipe.size();
        } else {
            return 0;
        }
    }

    public class MainBaking extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cv;
        ImageView itemImage;
        TextView itemTitle;
        TextView itemServings;

        public MainBaking(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.mainCardView);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemServings = itemView.findViewById(R.id.itemServings);
            itemImage = itemView.findViewById(R.id.itemImage);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v){
            int pos = getAdapterPosition();
            Recipe clickedOnRecipe = mRecipe.get(getAdapterPosition());
            mRecipeClickLis.onRecipeCardClick(pos, clickedOnRecipe);

        }
    }
}
