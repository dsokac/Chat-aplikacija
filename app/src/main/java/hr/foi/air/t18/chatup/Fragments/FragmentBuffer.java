package hr.foi.air.t18.chatup.Fragments;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

public class FragmentBuffer
{
    private ArrayList<Fragment> fragments;
    private ArrayList<String> tags;

    public FragmentBuffer()
    {
        fragments = new ArrayList<>();
        tags = new ArrayList<>();
    }

    public void add(Fragment fragment, String tag)
    {
        fragments.add(fragment);
        tags.add(tag);
    }

    public Fragment getFragment(int position)
    {
        return fragments.get(position);
    }

    public String getTag(int position)
    {
        return tags.get(position);
    }

    public int count()
    {
        return fragments.size();
    }
}
