package hr.foi.air.t18.chatup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import hr.foi.air.t18.chatup.Fragments.FragmentBuffer;
import hr.foi.air.t18.chatup.Fragments.HomePageFragment;
import hr.foi.air.t18.chatup.Fragments.MessagesFragment;
import hr.foi.air.t18.chatup.Fragments.SearchFragment;

/**
 * View pager adapter for the swipe tabs feature
 * Created by Laptop on 9.11.2015..
 */
public class PagerAdapter extends FragmentStatePagerAdapter
{
    private FragmentBuffer buffer;

    /**
     * Constructor
     * @param fm
     * @param buffer
     */
    public PagerAdapter(FragmentManager fm, FragmentBuffer buffer)
    {
        super(fm);
        this.buffer = buffer;
    }

    /**
     * Method that initializes the fragments as per their location
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position)
    {

        return buffer.getFragment(position);
    }

    @Override
    public int getCount()
    {
        return buffer.count();
    }
}
