package hr.foi.air.t18.chatup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Simple Fragment for Users Home Page
 * Created by Laptop on 9.11.2015..
 */
public class HomePageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.tab_fragment_main, container, false);
        View root = inflater.inflate(R.layout.tab_fragment_main, container, false);
        ArrayList<String> friends = new ArrayList<>();
        friends.add("neki");
        friends.add("neki novi");
        friends.add("moj prijatelj");
        friends.add("neki čudni");
        friends.add("posao ante");
        friends.add("susjed");
        friends.add("drugi susjed");
        friends.add("treći susjed");
        friends.add("neki");
        friends.add("neki novi");
        friends.add("moj prijatelj");
        friends.add("neki čudni");
        friends.add("posao ante");
        friends.add("susjed");
        friends.add("drugi susjed");
        friends.add("treći susjed");

        ListView lv =  (ListView)root.findViewById(R.id.friendListView);
        lv.setAdapter(new UserListAdapter(getActivity(), friends));
        return root;
    }


}
