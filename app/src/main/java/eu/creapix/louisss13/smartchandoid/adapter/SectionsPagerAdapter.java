package eu.creapix.louisss13.smartchandoid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers.UserInfoParser;
import eu.creapix.louisss13.smartchandoid.fragment.PlaceholderFragment;

/**
 * Created by arnau on 05-01-18.
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
