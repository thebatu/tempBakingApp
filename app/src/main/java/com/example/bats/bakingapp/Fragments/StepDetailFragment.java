package com.example.bats.bakingapp.Fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bats.bakingapp.Models.Recipe;
import com.example.bats.bakingapp.Models.Steps;
import com.example.bats.bakingapp.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
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
    @BindView(R.id.left_arrow) ImageButton leftArrow;
    @BindView(R.id.right_arrow) ImageButton rightArrow;
    @BindView(R.id.tv_step_description) TextView tv_StepDescription;
    String tvStepDesciptionString;
    public StepChangeClickListener stepChangeClickListener;
    private SimpleExoPlayer simpleExoPlayer;
    private ArrayList<Steps> stepsList;
    int stepIndex;
    long position = 0;
    Uri uri;
    String videoURL;
    Bundle bundle;

    public StepDetailFragment() {}

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, rootView);

        bundle = savedInstanceState;

        int orientation = getResources().getConfiguration().orientation;
        int minSize = getResources().getConfiguration().smallestScreenWidthDp;


        if (orientation == 1 && minSize <= 600) {
            Toolbar toolbar = rootView.findViewById(R.id.toolbar);
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    assert getFragmentManager() != null;
                    getActivity().onBackPressed();
                    getActivity().finish();
                }
            });

        }


        //handle description visible or not depending on orientation
        if (orientation == 2 && minSize < 600) {
            tv_StepDescription.setVisibility(View.GONE);
        }

        tv_StepDescription.setText(tvStepDesciptionString);

        position = C.TIME_UNSET;

        rightArrow.setOnClickListener(this);
        leftArrow.setOnClickListener(this);

        //Exo player logic to handle if there is a URL to play the video or if the URL is null
        if (savedInstanceState == null) {
            if (videoURL != null) {
                if (!videoURL.equals("")) {
                    Uri uri = Uri.parse(videoURL).buildUpon().build();
                    initExoPlayer(uri, savedInstanceState);
                }

            }else {
                exoPlayer.hideController();
                exoPlayer.setDefaultArtwork(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.novideo));
            }
            tv_StepDescription.setText(tvStepDesciptionString);
            //else URL not null, play video
        } else {
            position = savedInstanceState.getLong("video_play_last_position");
            stepsList = savedInstanceState.getParcelableArrayList("stepsReceived");
            videoURL = savedInstanceState.getString("videoURL");
            if (videoURL != null) {
                if (!videoURL.equals("")) {
                    uri = Uri.parse(videoURL).buildUpon().build();
                    initExoPlayer(uri, savedInstanceState);
                }
            }else{
                exoPlayer.setDefaultArtwork(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.novideo));
            }
            tv_StepDescription.setText(savedInstanceState.getString("stepDescription"));
        }

        return rootView;
    }


    //left and right arrow click handler for next or previous video
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.right_arrow:
                if (stepIndex + 1 == stepsList.size()) {
                    Toast.makeText(getActivity(), "Recipe Completed", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    stepChangeClickListener.stepChangeClickListener(stepIndex + 1);
                    return;
                }
            case R.id.left_arrow:
                if (stepIndex == 0) {
                    Toast.makeText(getActivity(), "You are on step #0", Toast.LENGTH_SHORT).show();
                } else {
                    stepChangeClickListener.stepChangeClickListener(stepIndex - 1);
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
    private void initExoPlayer(Uri uri, @Nullable Bundle savedInstanceState){
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

            if (savedInstanceState != null){
                Long l = savedInstanceState.getLong("video_play_last_position");
                simpleExoPlayer.seekTo(l);

                int playerPlayBackState = savedInstanceState.getInt("playerPlayState");
                Log.d(TAG, "initExoPlayer:   "  + playerPlayBackState);
            }
            else {
                if (position != C.TIME_UNSET) {
                    simpleExoPlayer.seekTo(position);
                }
            }

            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
        }

    }

    // before API lvl 24 we release the player early coz there is no guarentee the system will call
    //onStop() so we terminate on onPause()
    @Override
    public void onPause() {
        super.onPause();
        if (simpleExoPlayer != null){
            position = simpleExoPlayer.getCurrentPosition();
        }

        releasePlayer();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (uri != null) {
            initExoPlayer(uri, bundle);
        }
        if (simpleExoPlayer != null) {
            Long mPosition = simpleExoPlayer.getCurrentPosition();
            if (mPosition!= C.TIME_UNSET)
                simpleExoPlayer.seekTo(mPosition);
        }
    }


    //stop exo player
    //activity no longer visible
    @Override
    public void onStop() {
        super.onStop();
        if (videoURL != null) {
            if (!videoURL.equals("")) {
                //Activity no longer visible on API +24
                if (Util.SDK_INT >= 24 && simpleExoPlayer != null){
                    releasePlayer();
                }

            }
        }
    }

    /**
     * Store video position and stop, release and nullify the player.
     */
    private void releasePlayer(){
        if (simpleExoPlayer != null) {
            position = simpleExoPlayer.getCurrentPosition();
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }

    }

    //setter for the URL and URI of a video
    public void setStepData(int step_position) {

        this.stepIndex = step_position;
        Steps actualStep = stepsList.get(stepIndex);
        if (actualStep.getVideoURL() != null && !actualStep.getVideoURL().equals("")) {

            videoURL = actualStep.getVideoURL();
            uri = Uri.parse(actualStep.getVideoURL()).buildUpon().build();
        }

        tvStepDesciptionString = actualStep.getDescription();

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
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("videoURL", videoURL);
        outState.putString("tvStepDesciptionString", tvStepDesciptionString);
        //long position = simpleExoPlayer.getCurrentPosition();
        outState.putLong("video_play_last_position", position);
        outState.putParcelableArrayList("stepsReceived", (ArrayList<? extends Parcelable>) stepsList);
        //outState.putInt("playerPlayState", simpleExoPlayer.getPlaybackState());
        super.onSaveInstanceState(outState);

    }
}
