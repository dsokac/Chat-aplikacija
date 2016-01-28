package hr.foi.air.t18.chatup.Fragments;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * Class that servers as a buffer for fragments. User of this
 * class can supply a tag to each fragment.
 */
public class FragmentBuffer
{
    private ArrayList<Fragment> fragments;
    private ArrayList<String> tags;

    /**
     * FragmentBuffer constructor.
     */
    public FragmentBuffer()
    {
        fragments = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Adds new fragment into the buffer with tag.
     * @param fragment Fragment object
     * @param tag String that serves as a tag
     */
    public void add(Fragment fragment, String tag)
    {
        fragments.add(fragment);
        tags.add(tag);
    }

    /**
     * Returns fragment depending on it's position.
     * @param position position of Fragment
     * @return Fragment object
     */
    public Fragment getFragment(int position)
    {
        return fragments.get(position);
    }

    /**
     * Returns tag depending on it's position.
     * @param position position of tag.
     * @return String that represents tag
     */
    public String getTag(int position)
    {
        return tags.get(position);
    }

    /**
     * Returns the number of fragments in FragmentBuffer.
     * @return fragment count
     */
    public int count()
    {
        return fragments.size();
    }
}
