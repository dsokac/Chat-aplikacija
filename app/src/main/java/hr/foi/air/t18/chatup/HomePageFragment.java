package hr.foi.air.t18.chatup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import hr.foi.air.t18.core.User;
import hr.foi.air.t18.webservice.FriendsAsync;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.WebServiceResult;

/**
 * Simple Fragment for Users Home Page
 * Created by Laptop on 9.11.2015..
 */
public class HomePageFragment extends Fragment {

    private ImageView profilePicture;
    private SharedPreferences sharedPref;
    private String loggedIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.tab_fragment_main, container, false);

        setRetainInstance(true);

        this.sharedPref = getActivity().getPreferences(getActivity().MODE_PRIVATE);
        this.loggedIn = sharedPref.getString("id","unknown");

        View root = inflater.inflate(R.layout.tab_fragment_main, container, false);
        final ArrayList<User> friends = new ArrayList<User>();

        profilePicture = (ImageView) root.findViewById(R.id.profilePicture);
        profilePicture.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent(getActivity(), ImagePickerActivity.class);
                startActivity(i);
                return true;
            }
        });


        final View final_root = root;
        FriendsAsync getFriends = new FriendsAsync(this.loggedIn, new IListener<JSONArray>() {
            @Override
            public void onBegin() {

            }

            @Override
            public void onFinish(WebServiceResult<JSONArray> result) {
                if (result.status == 0) {
                    try {
                        JSONArray json = result.data;
                        for (int i = 0; i < json.length(); i++) {
                            JSONObject currentUser = json.getJSONObject(i);
                            friends.add(new User(currentUser.getString("id"), currentUser.getString("id"), currentUser.getString("status")));

                        }
                        //generates list items for friend list view
                        ListView lv = (ListView) final_root.findViewById(R.id.friendListView);
                        lv.setAdapter(new UserListAdapter(getActivity(), friends));
                    } catch (Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), result.message, Toast.LENGTH_LONG).show();

                }
            }
        });

        getFriends.execute();

        return root;
    }
}
