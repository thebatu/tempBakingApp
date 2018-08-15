package com.example.bats.bakingapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bats.bakingapp.Activities.MainActivity;
import com.example.bats.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Class for displaying ViewPager consisting of recipe steps and recipe ingredients
 */

public class IngredientsStepsFragment extends Fragment{

    @BindView(R.id.pager) ViewPager mPager;
    @BindView(R.id.tabs) TabLayout tabLayout;

    String recipe;

    public IngredientsStepsFragment(){}

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredients_steps_pager_layout, container, false);
        ButterKnife.bind(this, view);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        //listener for clicks on Pager.
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                int currentItem = mPager.getCurrentItem();
                if (currentItem == 0) {
                    Intent goToMainActivity = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    goToMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(goToMainActivity);
                }
                else
                    mPager.setCurrentItem(0);
            }
        });


        // Instantiate a ViewPager and a PagerAdapter.
        recipe = getArguments().getString("recipe_string");
        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(getFragmentManager());
        tabLayout.setupWithViewPager(mPager);
        tabLayout.setupWithViewPager(mPager);
        mPager.setAdapter(tabsPagerAdapter);

        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;

    }


    /**
     * pagerView class to display recipe steps and instructions on phones
     */
    private class TabsPagerAdapter extends FragmentPagerAdapter {

        private int NUM_ITEMS = 2;




        public TabsPagerAdapter(FragmentManager fm){
            super(fm);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.ingredients);
                case 1:
                    return getString(R.string.steps);
                default:
                    return getString(R.string.na);
            }
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putString("recipe_string", recipe);

            IngredientsListFragment firstFragment =  new IngredientsListFragment();
            firstFragment.setArguments(bundle);

            StepsListFragment secondFragment = new StepsListFragment();
            secondFragment.setArguments(bundle);

            switch (position) {
                case 0:
                    return firstFragment;
                case 1:
                    return secondFragment;
                default:
                    return firstFragment;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }






}
