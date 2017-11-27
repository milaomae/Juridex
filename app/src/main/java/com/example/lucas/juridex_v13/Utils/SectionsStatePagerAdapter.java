package com.example.lucas.juridex_v13.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lucas on 25/11/2017.
 */

public class SectionsStatePagerAdapter extends FragmentStatePagerAdapter{
    private static final String TAG = "SectionsStatePagerAdapt";

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final HashMap<Fragment, Integer> mFragmemts = new HashMap<>();
    private final HashMap<String, Integer> mFragmemtsNumbers = new HashMap<>();
    private final HashMap<Integer, String> mFragmemtsNames = new HashMap<>();
    private final List<Integer> mNiveis = new ArrayList<>();


    public SectionsStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String fragmentName){
        mFragmentList.add(fragment);
        mFragmemts.put(fragment, mFragmentList.size()-1);
        mFragmemtsNumbers.put(fragmentName, mFragmentList.size()-1);
        mFragmemtsNames.put(mFragmentList.size()-1, fragmentName);
    }

    public void addFragment(Fragment fragment, String fragmentName, int nivel){
        mFragmentList.add(fragment);
        mFragmemts.put(fragment, mFragmentList.size()-1);
        mFragmemtsNumbers.put(fragmentName, mFragmentList.size()-1);
        mFragmemtsNames.put(mFragmentList.size()-1, fragmentName);
        mNiveis.add(nivel);
    }

    public Integer getFragmentNumber(String fragmentName){
        if(mFragmemtsNumbers.containsKey(fragmentName))
            return mFragmemtsNumbers.get(fragmentName);
        else
            return null;
    }

    public Integer getFragmentNumber(Fragment fragment){
        if(mFragmemtsNumbers.containsKey(fragment))
            return mFragmemtsNumbers.get(fragment);
        else
            return null;
    }

    public String getFragmentName(Integer fragmentNumber){
        if(mFragmemtsNames.containsKey(fragmentNumber))
            return mFragmemtsNames.get(fragmentNumber);
        else
            return null;
    }
}
