package com.example.bats.bakingapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bats.bakingapp.Models.Recipe;
import com.example.bats.bakingapp.Models.Steps;
import com.example.bats.bakingapp.R;

import java.util.ArrayList;

public class RecyclerStepsAdapter extends RecyclerView.Adapter<RecyclerStepsAdapter.StepsViewHolder>{

    Context mContext;
    Recipe recipe;
    ArrayList<Steps> stepsList;
    final private StepOnclickListener mStepOnClick;


    public interface StepOnclickListener{
        void onClick(Steps clickedOnStep);
    }

    public RecyclerStepsAdapter(Context context, Recipe recipe, StepOnclickListener stepOnClickListener) {
        this.mContext = context;
        this.recipe = recipe;
        this.stepsList = recipe.getSteps();
        this.mStepOnClick = stepOnClickListener;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_steps, parent, false);
        return new StepsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        Steps step = stepsList.get(position);
        holder.tv_steps_index.setText(String.valueOf(position + 1));
        holder.tv_steps_title.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (stepsList == null) {return 0;}
        return stepsList.size();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_steps_index;
        TextView tv_steps_title;

        public StepsViewHolder(View itemView) {
            super(itemView);
            tv_steps_index = itemView.findViewById(R.id.tv_steps_index);
            tv_steps_title = itemView.findViewById(R.id.tv_steps_title);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            mStepOnClick.onClick(stepsList.get(getAdapterPosition()));
        }
    }
}
