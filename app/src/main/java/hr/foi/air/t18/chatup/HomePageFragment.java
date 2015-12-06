package hr.foi.air.t18.chatup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;


import java.util.ArrayList;

import hr.foi.air.t18.core.User;

/**
 * Simple Fragment for Users Home Page
 * Created by Laptop on 9.11.2015..
 */
public class HomePageFragment extends Fragment {

    private ImageView profilePicture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.tab_fragment_main, container, false);

        setRetainInstance(true);

        View root = inflater.inflate(R.layout.tab_fragment_main, container, false);
        ArrayList<User> friends = new ArrayList<User>();

        profilePicture = (ImageView)root.findViewById(R.id.profilePicture);
        profilePicture.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent(getActivity(), ImagePickerActivity.class);
                startActivity(i);
                return true;
            }
        });

        /***
         * Temporary added list items
         */
        User newUser = new User();
        newUser.setUsername("neki");
        newUser.setStatus("online");
        friends.add(newUser);
        User newUser1 = new User();
        newUser1.setUsername("neki novi");
        newUser1.setStatus("online");
        friends.add(newUser1);
        User newUser2 = new User();
        newUser2.setUsername("prvi sused");
        newUser2.setStatus("online");
        friends.add(newUser2);
        User newUser3 = new User();
        newUser3.setUsername("drugi sused");
        newUser3.setStatus("offline");
        friends.add(newUser3);

        //generates list items for friend list view
        ListView lv =  (ListView)root.findViewById(R.id.friendListView);
        lv.setAdapter(new UserListAdapter(getActivity(), friends));
        return root;
    }


}
