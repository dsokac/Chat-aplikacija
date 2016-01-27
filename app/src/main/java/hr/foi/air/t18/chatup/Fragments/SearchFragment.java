package hr.foi.air.t18.chatup.Fragments;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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

import hr.foi.air.t18.chatup.States.SearchEmail;
import hr.foi.air.t18.chatup.States.SearchUsername;
import hr.foi.air.t18.core.SharedPreferencesClass;
import hr.foi.air.t18.core.State.Context;
import hr.foi.air.t18.core.State.IState;
import hr.foi.air.t18.socketnotifications.BackgroundService;
import hr.foi.air.t18.socketnotifications.ConnectToService;
import hr.foi.air.t18.chatup.R;
import hr.foi.air.t18.chatup.RegisteredUsersListAdapter;
import hr.foi.air.t18.socketnotifications.FriendRequestNotifsAsync;
import hr.foi.air.t18.socketnotifications.SocketNotificationsManager;
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
    ArrayList<User> reg_users = new ArrayList<User>();
    ArrayList<User> reg_users2 = new ArrayList<User>();
    final ArrayList<User> search = new ArrayList<User>();
    final ArrayList<User> search2 = new ArrayList<User>();
    public static Button search_button;
    public static Button search_button2;
    private String current="";
    public static EditText search_text;

    private SocketNotificationsManager socketNotificationsManager = null;

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
        search_text = (EditText) root.findViewById(R.id.searchUser);

        socketNotificationsManager  = (SocketNotificationsManager) MiddleMan.getNotificationObject();

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

        JSONObject json = new JSONObject();
        try
        {
            json.put("from",this.loggedIn);
            json.put("to",this.selected_friend);
        }catch(Exception ex)
        {
            Log.e("error", "onCreate: ", ex);
        }

        this.socketNotificationsManager.attachAsyncTasks(new FriendRequestNotifsAsync(this.socketNotificationsManager,json));
    }

    //onCreateContextMenu logic
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.add_friends_menu, menu);
        position_in_list = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;

        selected_friend = toString();
    }
    //Returns selected user on long click

    public String toString() {

        return (reg_users.get((int)position_in_list).getEmail());
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

    @Override
    public void onStart() {
        super.onStart();
        Context ctx = new Context();

        String searchOption = SharedPreferencesClass.getDefaults(getString(R.string.SettingsSearch), getActivity().getApplicationContext());

        IState state = null;

        if(searchOption.contentEquals(getString(R.string.SettingsRadioEmail))) state = new SearchEmail(search,lv,search_text.getText().toString(),getActivity(), reg_users2);
        else state = new SearchUsername(search2,lv,search_text.getText().toString(),getActivity(), reg_users);

        state.applyChange(ctx, search_button);
        this.reg_users = (ArrayList<User>)state.getData();

    }
}



