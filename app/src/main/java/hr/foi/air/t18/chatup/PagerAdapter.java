package hr.foi.air.t18.chatup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * View pager adapter for the swipe tabs feature
 * Created by Laptop on 9.11.2015..
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    /**
     * Constructor
     * @param fm
     * @param NumOfTabs
     */
    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    /**
     * Method that initializes the fragments as per their location
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                HomePageFragment tab1 = new HomePageFragment();
                return tab1;
            case 1:
                SearchFragment tab2 = new SearchFragment();
                return tab2;
            case 2:
                MessagesFragment tab3 = new MessagesFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
