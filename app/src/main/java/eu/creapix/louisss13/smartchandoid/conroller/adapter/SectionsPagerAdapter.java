package eu.creapix.louisss13.smartchandoid.conroller.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import eu.creapix.louisss13.smartchandoid.conroller.fragment.PlaceholderFragment;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.UserInfoParser;

/**
 * Created by Arnaud Baes on 05-01-18.
 * IG-3C 2017 - 2018
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private UserInfoParser[] userInfos;

    public SectionsPagerAdapter(FragmentManager fm, UserInfoParser[] userInfos) {
        super(fm);
        this.userInfos = userInfos;
    }

    @Override
    public Fragment getItem(int position) {
        PlaceholderFragment placeholderFragment = new PlaceholderFragment();
        placeholderFragment.initData(userInfos[position]);
        return placeholderFragment;
    }

    @Override
    public int getCount() {
        return userInfos.length;
    }
}
