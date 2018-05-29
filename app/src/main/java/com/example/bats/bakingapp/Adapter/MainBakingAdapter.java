package com.example.bats.bakingapp.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bats.bakingapp.Models.Recipe;
import com.example.bats.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bats on 5/28/18.
 *
 */

public class MainBakingAdapter extends RecyclerView.Adapter<MainBakingAdapter.MainBaking> {

    private ArrayList<Recipe> mRecipe;
    private Context context;
    Recipe recipe;

    private final recipeClickListener mRecipeClickLis;

    public MainBakingAdapter (Context context, recipeClickListener listener) {
        this.context = context;
        mRecipeClickLis = listener;
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
        holder.tv.setText(recipe.getName());


    }

    public void setRecipeData(ArrayList recipe) {
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
        TextView tv;

        public MainBaking(View itemView) {
            super(itemView);

            cv = itemView.findViewById(R.id.mainCardView);
//            cv.setOnClickListener(this);
            tv = itemView.findViewById(R.id.itemTitle);
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
