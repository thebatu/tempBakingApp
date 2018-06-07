package com.example.bats.bakingapp.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment{

    @BindView(R.id.simple_exo_player) SimpleExoPlayerView exoPlayer;
    @BindView(R.id.left_arrow) ImageButton left_arrow;
    @BindView(R.id.right_arrow) ImageButton right_arrow;
    @BindView(R.id.tv_step_description) TextView tv_step_desciption;
    private SimpleExoPlayer simpleExoPlayer;
    private List<Steps> stepsList;
    int step_index = 0;
    private long position = 0;
    Uri uri;


    public StepDetailFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, rootView);

        String string_recipe = getArguments().getString("recipe_string");
        String string_step = getArguments().getString("step_string");

        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(string_recipe, Recipe.class);
        Steps step = gson.fromJson(string_step, Steps.class);

        stepsList = recipe.getSteps();
        step_index = step.getId();

        position = C.TIME_UNSET;

        uri = Uri.parse(step.getVideoURL()).buildUpon().build();

        initExoPlayer(uri);

        return rootView;


    }

    private void initExoPlayer(Uri uri){
        if (simpleExoPlayer == null) {
            exoPlayer .requestFocus();

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


}
