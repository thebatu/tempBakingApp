package com.example.bats.bakingapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.bats.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientsStepsFragment extends Fragment{

    @BindView(R.id.pager) ViewPager mPager;
    @BindView(R.id.tabs) TabLayout tabLayout;

    String recipe;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredients_steps_pager_layout, container, false);

        ButterKnife.bind(this, view);
//
//        android.support.v7.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        // Instantiate a ViewPager and a PagerAdapter.
        recipe = getArguments().getString("recipe_string");
        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(getFragmentManager());
        tabLayout.setupWithViewPager(mPager);
        tabLayout.setupWithViewPager(mPager);
        mPager.setAdapter(tabsPagerAdapter);

        return view;

    }

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
