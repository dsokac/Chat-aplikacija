package hr.foi.air.t18.chatup.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import hr.foi.air.t18.chatup.SocketNotifications.BackgroundService;
import hr.foi.air.t18.chatup.SocketNotifications.ConnectToService;
import hr.foi.air.t18.chatup.R;
import hr.foi.air.t18.chatup.RegisteredUsersListAdapter;
import hr.foi.air.t18.chatup.SocketNotifications.SocketNotificationsManager;
import hr.foi.air.t18.core.MiddleMan;
import hr.foi.air.t18.core.User;
import hr.foi.air.t18.webservice.MainAsync.AddFriendAsync;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.MainAsync.SearchAsync;
import hr.foi.air.t18.webservice.WebServiceResult;

/**
 *  Public class SearchFragment implements logic of fragment Search
 * Created by Laptop on 9.11.2015..
 * Updated  by JurmanLap 18.12.2015.
 */
public class SearchFragment extends Fragment {

    private SharedPreferences sharedPref;
    private String loggedIn;
    ListView lv;
    private String selected_friend="";
    long position_in_list;
    final ArrayList<User> reg_users = new ArrayList<User>();
    final ArrayList<User> reg_users2 = new ArrayList<User>();
    final ArrayList<User> search = new ArrayList<User>();
    final ArrayList<User> search2 = new ArrayList<User>();
    public static Button search_button;
    public static Button search_button2;
    private String current="";
    public static EditText search_text;

    private BackgroundService mService;
    private ConnectToService mConnection;
    private SocketNotificationsManager socketNotificationsManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setRetainInstance(true);
        this.sharedPref = getActivity().getPreferences(getActivity().MODE_PRIVATE);
        //get values of logged in user
        this.loggedIn = sharedPref.getString("id","unknown");

        //find references
        View root = inflater.inflate(R.layout.tab_fragment_search, container, false);
        final View final_root = root;
        search_button = (Button) root.findViewById(R.id.searchButton);
        search_button2 = (Button) root.findViewById(R.id.searchButton2);
        search_text = (EditText) root.findViewById(R.id.searchUser);

        //socketNotificationsManager  = (SocketNotificationsManager) MiddleMan.getObject();

        //logic when user click on search button
        search_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                reg_users.clear();
                lv = (ListView) final_root.findViewById(R.id.searchListview);
                for (int i = 0; i < search.size(); i++) {
                    if(search.get(i).getEmail().contains(search_text.getText().toString())){
                        reg_users.add(search.get(i));
                    }

                }
                lv.setAdapter(new RegisteredUsersListAdapter(getActivity(), reg_users));
                current="onClick1";
            }
        });

        search_button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                reg_users2.clear();
                lv = (ListView) final_root.findViewById(R.id.searchListview);
                for (int i = 0; i < search2.size(); i++) {
                    if(search2.get(i).getEmail().contains(search_text.getText().toString())){
                        reg_users2.add(search2.get(i));
                    }

                }
                lv.setAdapter(new RegisteredUsersListAdapter(getActivity(), reg_users2));
                current="onClick2";
            }
        });


        //fetching all registered users
        SearchAsync registeredUsers = new SearchAsync(this.loggedIn, new IListener<JSONArray>() {
            /***
             * Overridden onBegin event of SearchAsync task defines what is happening when async task starts to execute.
             */
            @Override
            public void onBegin() {
            }

            /***
             * Overridden onFinish event of SearchAsync task defines what happens when async task finish execution.
             * @param //status - status integer returned by web service
             * @param //message - message string returned by web service
             * @param //data - data returned by web service
             */
            @Override
            public void onFinish(WebServiceResult<JSONArray> result) {
                if (result.status == 0) {
                    try {
                        JSONArray json = result.data;
                        //store data results of web service into ArrayList <User> search (only id's/email's)
                        for (int i = 0; i < json.length(); i++) {
                            JSONObject currentUser = json.getJSONObject(i);
                            search.add(new User(currentUser.getString("id"), currentUser.getString("id")));
                            search2.add(new User(currentUser.getString("id"), currentUser.getString("username")));
                        }
                        //register ListView for Context Menu
                        lv = (ListView) final_root.findViewById(R.id.searchListview);
                        lv.setLongClickable(true);

                        //setting values of ArrayList <User> search into ListView

                        registerForContextMenu(lv);
                    } catch (Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), result.message, Toast.LENGTH_LONG).show();
                }
            }
        });
        registeredUsers.execute();
        return root;
    }

    /**
     *  Private function for adding friends
     */
    private void AddFriend()
    {
        AddFriendAsync addFriendAsync = new AddFriendAsync(loggedIn,selected_friend, new IListener<Void>() {
            /***
             * Overridden onBegin event of AddFriendAsync task defines what is happening when async task starts to execute.
             */
            @Override
            public void onBegin() {
            }

            /***
             * Overridden onFinish event of AddFriendAsync task defines what happens when async task finish execution.
             */
            @Override
            public void onFinish(WebServiceResult<Void> wsResult) {

                if(wsResult.status == 0)
                {
                }
            }
        });
        addFriendAsync.execute();
        //mConnection.connectedService().useSocket().NotifyFriendRequest(this.loggedIn, selected_friend);
    }

    //onCreateContextMenu logic
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.add_friends_menu, menu);
        position_in_list = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
        //store value of selected frend into variable
        if (current.equals("onClick1")) {
            selected_friend = toString();
        }
        else if (current.equals("onClick2")) {
            selected_friend = toString2();
        }
    }
    //Returns selected user on long click

    public String toString() {

        return (reg_users.get((int)position_in_list).getEmail());
    }

    public String toString2() {

        return (reg_users2.get((int)position_in_list).getEmail());
    }

    //onContextItemSelected logic
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_friend_add:
                Toast.makeText(getActivity().getApplicationContext(), "Adding friend...", Toast.LENGTH_SHORT).show();
                AddFriend();
                return true;
            case R.id.add_friend_cancel:
                Toast.makeText(getActivity().getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


}
