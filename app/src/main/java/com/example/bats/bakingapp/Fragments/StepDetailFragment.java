package com.example.bats.bakingapp.Fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.example.bats.bakingapp.Models.Recipe;
import com.example.bats.bakingapp.Models.Steps;
import com.example.bats.bakingapp.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;

public class StepDetailFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.simple_exo_player) SimpleExoPlayerView exoPlayer;
    @BindView(R.id.left_arrow) ImageButton left_arrow;
    @BindView(R.id.right_arrow) ImageButton right_arrow;
    @BindView(R.id.tv_step_description) TextView tv_step_desciption;
    String tv_step_desciptionString;
    private StepChangeClickListener stepChangeClickListener;
    private SimpleExoPlayer simpleExoPlayer;
    private List<Steps> stepsList;
    int step_index;
    private long position = 0;
    Uri uri;
    String string_recipe;
    String videoURL;

    public StepDetailFragment() {}



    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, rootView);

//        string_recipe = getArguments().getString("recipe_string");
//        String string_step = getArguments().getString("step_string");
//
//        Gson gson = new Gson();
//        Recipe recipe = gson.fromJson(string_recipe, Recipe.class);
//        Steps step = gson.fromJson(string_step, Steps.class);
//        stepsList = recipe.getSteps();
        //step_index = step.getId() + 1;


        int orientation = getResources().getConfiguration().orientation;
        int minSize = getResources().getConfiguration().smallestScreenWidthDp;

        if (orientation == 2 && minSize < 600) {
            tv_step_desciption.setVisibility(View.GONE);
        } else if (orientation == 1) {
            tv_step_desciption.setVisibility(View.VISIBLE);
        }


        tv_step_desciption.setText(tv_step_desciptionString);

        position = C.TIME_UNSET;

        if (videoURL != null &&  !videoURL.isEmpty()){
            Uri uri = Uri.parse(videoURL).buildUpon().build();
            initExoPlayer(uri);
        }

        right_arrow.setOnClickListener(this);
        left_arrow.setOnClickListener(this);

        if (savedInstanceState == null) {
            if (videoURL != null) {
                if (!videoURL.equals("")) {
                    Uri uri = Uri.parse(videoURL).buildUpon().build();
                    initExoPlayer(uri);
                }else{
                    exoPlayer.hideController();
                    exoPlayer.setDefaultArtwork(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.novideo));
                }
            }
            tv_step_desciption.setText(tv_step_desciptionString);
        } else {
            position = savedInstanceState.getLong("video_state");
            stepsList = (List) savedInstanceState.getParcelableArrayList("stepsReceived");
            videoURL = savedInstanceState.getString("videoUrl");
            if (videoURL != null) {
                if (!videoURL.equals("")) {
                    uri = Uri.parse(videoURL).buildUpon().build();
                    initExoPlayer(uri);
                }
            }
            tv_step_desciption.setText(savedInstanceState.getString("stepDescription"));
        }







        return rootView;

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.right_arrow:
                if (step_index + 1 == stepsList.size()) {
                    Toast.makeText(getActivity(), "Recipe Completed", Toast.LENGTH_SHORT).show();
                } else {
                    stepChangeClickListener.stepChangeClickListener(step_index + 1);
                    return;
                }
            case R.id.left_arrow:
                if (step_index + 1 == stepsList.size()) {
                    Toast.makeText(getActivity(), "Recipe Completed", Toast.LENGTH_SHORT).show();
                } else {
                    stepChangeClickListener.stepChangeClickListener(step_index - 1);
                    return;
                }
            default:

        }
    }


    public void setStepsList(String recipe){
        Gson gson = new Gson();
        Recipe recipe_obj = gson.fromJson(recipe, Recipe.class);
        stepsList = recipe_obj.getSteps();
    }


    private void initExoPlayer(Uri uri){
        if (simpleExoPlayer == null) {
            exoPlayer.requestFocus();

            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            exoPlayer.setPlayer(simpleExoPlayer);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            if (position != C.TIME_UNSET) {
                simpleExoPlayer.seekTo(position);
            }
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initExoPlayer(uri);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!videoURL.equals("")) {
            simpleExoPlayer.stop();
        }
    }

    public void setStepData(int step_position) {

        this.step_index = step_position;
        Steps actualStep = stepsList.get(step_index);
        if (actualStep.getVideoURL() != null && !actualStep.getVideoURL().equals("")) {

            videoURL = actualStep.getVideoURL();
            uri = Uri.parse(actualStep.getVideoURL()).buildUpon().build();
        }

        tv_step_desciptionString = actualStep.getDescription();


    }

    public interface StepChangeClickListener{
        void  stepChangeClickListener(int newPOS);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            stepChangeClickListener = (StepChangeClickListener) context;
        } catch (Exception e) {
            Log.d(TAG, "onAttach: " + e.getMessage());
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("videoURI", videoURL);
        outState.putString("tv_step_desciptionString",tv_step_desciptionString);
        long position = simpleExoPlayer.getCurrentPosition();
        outState.putLong("video_play_last_position", position);
        outState.putParcelableArrayList("stepsReceived", (ArrayList<? extends Parcelable>) stepsList);
        super.onSaveInstanceState(outState);


    }
}
