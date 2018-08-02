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
import com.example.bats.bakingapp.Models.Recipe;
import com.example.bats.bakingapp.Models.Steps;
import com.example.bats.bakingapp.R;
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

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;


/***
 * the fragment where displaying the video is handled for the tablet or phone. Exoplayer
 * is instantiated here and released here.
 */

public class StepDetailFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.simple_exo_player) SimpleExoPlayerView exoPlayer;
    @BindView(R.id.left_arrow) ImageButton left_arrow;
    @BindView(R.id.right_arrow) ImageButton right_arrow;
    @BindView(R.id.tv_step_description) TextView tv_step_description;
    String tv_step_desciptionString;
    public StepChangeClickListener stepChangeClickListener;
    private SimpleExoPlayer simpleExoPlayer;
    private ArrayList<Steps> stepsList;
    int step_index;
    private long position = 0;
    Uri uri;
    String videoURL;

    public StepDetailFragment() {}

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, rootView);

        int orientation = getResources().getConfiguration().orientation;
        int minSize = getResources().getConfiguration().smallestScreenWidthDp;

        //handle description visible or not depending on orientation
        if (orientation == 2 && minSize < 600) {
            tv_step_description.setVisibility(View.GONE);
        } else if (orientation == 1) {
            tv_step_description.setVisibility(View.VISIBLE);
        }

        tv_step_description.setText(tv_step_desciptionString);

        position = C.TIME_UNSET;

        //build player if null
        if (videoURL != null &&  !videoURL.isEmpty()){
            Uri uri = Uri.parse(videoURL).buildUpon().build();
            initExoPlayer(uri);
        }

        right_arrow.setOnClickListener(this);
        left_arrow.setOnClickListener(this);

        //Exo player logic to handle if there is a URL to play the video or if the URL is null
        if (savedInstanceState == null) {
            if (videoURL != null) {
                if (!videoURL.equals("")) {
                    Uri uri = Uri.parse(videoURL).buildUpon().build();
                    initExoPlayer(uri);
                }

            }else {
            exoPlayer.hideController();
            exoPlayer.setDefaultArtwork(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.novideo));
        }
            tv_step_description.setText(tv_step_desciptionString);
            //else URL not null, play video
        } else {
            position = savedInstanceState.getLong("video_state");
            stepsList = savedInstanceState.getParcelableArrayList("stepsReceived");
            videoURL = savedInstanceState.getString("videoURL");
            if (videoURL != null) {
                if (!videoURL.equals("")) {
                    uri = Uri.parse(videoURL).buildUpon().build();
                    initExoPlayer(uri);
                }
            }else{
                exoPlayer.setDefaultArtwork(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.novideo));
            }
            tv_step_description.setText(savedInstanceState.getString("stepDescription"));
            String sdafsd = "sdfsdfsdfs";
        }


        return rootView;

    }


    //left and right arrow click handler for next or previous video
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.right_arrow:
                if (step_index + 1 == stepsList.size()) {
                    Toast.makeText(getActivity(), "Recipe Completed", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    stepChangeClickListener.stepChangeClickListener(step_index + 1);
                    return;
                }
            case R.id.left_arrow:
                if (step_index == 0) {
                    Toast.makeText(getActivity(), "You are on step #0", Toast.LENGTH_SHORT).show();
                } else {
                    stepChangeClickListener.stepChangeClickListener(step_index - 1);
                    return;
                }
            default:

        }
    }

    //setter for the total steps in a recipe
    public void setStepsList(String recipe){
        Gson gson = new Gson();
        Recipe recipe_obj = gson.fromJson(recipe, Recipe.class);
        stepsList = recipe_obj.getSteps();
    }


    //init exo player
    private void initExoPlayer(Uri uri){
        if (simpleExoPlayer == null) {
            exoPlayer.requestFocus();

            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            exoPlayer.setPlayer(simpleExoPlayer);
            MediaSource mediaSource;
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            if (uri == null) {
                mediaSource = new ExtractorMediaSource(Uri.EMPTY, new DefaultDataSourceFactory(
                        getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            }else {
                mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(
                        getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            }


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

    //stop exo player
    @Override
    public void onStop() {
        super.onStop();
        if (videoURL != null) {
            if (!videoURL.equals("")) {
                simpleExoPlayer.stop();
            }
        }
    }

    //setter for the URL and URI of a video
    public void setStepData(int step_position) {

        this.step_index = step_position;
        Steps actualStep = stepsList.get(step_index);
        if (actualStep.getVideoURL() != null && !actualStep.getVideoURL().equals("")) {

            videoURL = actualStep.getVideoURL();
            uri = Uri.parse(actualStep.getVideoURL()).buildUpon().build();
        }

        tv_step_desciptionString = actualStep.getDescription();

    }


    //check if listener is really attached
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            stepChangeClickListener = (StepChangeClickListener) context;
        } catch (Exception e) {
            Log.d(TAG, "onAttach: " + e.getMessage());
        }
    }

    public interface StepChangeClickListener{
        void  stepChangeClickListener(int newPOS);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("videoURL", videoURL);
        outState.putString("tv_step_desciptionString",tv_step_desciptionString);
        long position = simpleExoPlayer.getCurrentPosition();
        outState.putLong("video_play_last_position", position);
        outState.putParcelableArrayList("stepsReceived", (ArrayList<? extends Parcelable>) stepsList);
        super.onSaveInstanceState(outState);


    }
}
