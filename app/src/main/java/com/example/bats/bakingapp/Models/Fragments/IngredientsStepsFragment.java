package com.example.bats.bakingapp.Models.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.bats.bakingapp.R;


public class IngredientsStepsFragment extends Fragment{

    private ViewPager mPager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredients_steps_pager_layout, container, false);

//        context = inflater.getContext();
//
//        android.support.v7.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) view.findViewById(R.id.pager);


        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(getFragmentManager());

        TabLayout tabLayout = view.findViewById(R.id.tabs);
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
        public Fragment getItem(int position) {
            IngredientsListFragment firstFragment =  new IngredientsListFragment();
            //firstFragment.setPagerNavigationInterface(ScreenSlidePagerActivity.this);

            StepsListFragment secondFragment = new StepsListFragment();
            //secondFragment.setPagerNavigationInterface(ScreenSlidePagerActivity.this);


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
