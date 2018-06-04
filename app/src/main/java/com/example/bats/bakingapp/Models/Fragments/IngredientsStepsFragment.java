package com.example.bats.bakingapp.Models.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentPagerAdapter;

public class IngredientsStepsFragment extends Fragment{

    private ViewPager mPager;
    private PagerAdapter myPagerAdapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredients_steps_fragment, container, false);


//        context = inflater.getContext();

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) view.findViewById(R.id.pager);

        // Instantiate a ViewPager and a PagerAdapter.
       // mPager.setAdapter(new myPagerAdapter(getActivity().getSupportFragmentManager()));


        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mPager);


        return view;


    }

//    private class myPagerAdapter extends FragmentPagerAdapter {
//
//        public myPagerAdapter(FragmentManager fm){
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            FirstPagerFragment firstFragment = FirstPagerFragment.newInstance();
//            firstFragment.setPagerNavigationInterface(ScreenSlidePagerActivity.this);
//
//            SecondPagerFragment secondFragment = SecondPagerFragment.newInstance();
//            secondFragment.setPagerNavigationInterface(ScreenSlidePagerActivity.this);
//
//
//            switch (position) {
//                case 0:
//                    return firstFragment;
//                case 1:
//                    return secondFragment;
//                default:
//                    return firstFragment;
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return 3;
//        }
//    }






}
