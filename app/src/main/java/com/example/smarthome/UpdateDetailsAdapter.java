package com.example.smarthome;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class UpdateDetailsAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragList = new ArrayList<>();
    private final List<String> fragTitleList = new ArrayList<>();

    public UpdateDetailsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        fragList.add(fragment);
        fragTitleList.add(title);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }
}
