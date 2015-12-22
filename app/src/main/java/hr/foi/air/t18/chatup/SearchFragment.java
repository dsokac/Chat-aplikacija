package hr.foi.air.t18.chatup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import hr.foi.air.t18.core.User;
import hr.foi.air.t18.webservice.AddFriendAsync;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.SearchAsync;
import hr.foi.air.t18.webservice.WebServiceResult;

/**
 * Created by Laptop on 9.11.2015..
 * Updated 18.12.2015.
 */
public class SearchFragment extends Fragment {

    private SharedPreferences sharedPref;
    private String loggedIn;
    ListView lv;
    private String selected_friend="";
    long position_in_list;
    final ArrayList<User> reg_users = new ArrayList<User>();
    final ArrayList<User> search = new ArrayList<User>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.tab_fragment_main, container, false);
        setRetainInstance(true);
        this.sharedPref = getActivity().getPreferences(getActivity().MODE_PRIVATE);
        this.loggedIn = sharedPref.getString("id","unknown");

        View root = inflater.inflate(R.layout.tab_fragment_search, container, false);
        final View final_root = root;
        Button search_button = (Button) root.findViewById(R.id.searchButton);
        final EditText search_text = (EditText) root.findViewById(R.id.searchUser);

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
            }
        });


        SearchAsync registeredUsers = new SearchAsync(this.loggedIn, new IListener<JSONArray>() {
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
                            search.add(new User(currentUser.getString("id"), currentUser.getString("id")));
                        }
                        lv = (ListView) final_root.findViewById(R.id.searchListview);
                        lv.setLongClickable(true);
                        lv.setAdapter(new RegisteredUsersListAdapter(getActivity(), reg_users));
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


    private void AddFriend()
    {
        AddFriendAsync addFriendAsync = new AddFriendAsync(loggedIn,selected_friend, new IListener<Void>() {

            @Override
            public void onBegin() {
            }

            @Override
            public void onFinish(WebServiceResult<Void> wsResult) {

                if(wsResult.status == 0)
                {
                }
            }
        });
        addFriendAsync.execute();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.add_friends_menu, menu);
        position_in_list=((AdapterView.AdapterContextMenuInfo)menuInfo).position;
        selected_friend=toString();
    }

    //Returns selected user on long click
    @Override
    public String toString() {
        return (reg_users.get((int)position_in_list).getEmail());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.add_friend_add:
                Toast.makeText(getActivity().getApplicationContext(), "moj id:"+loggedIn+"\nselektirani:"+selected_friend, Toast.LENGTH_SHORT).show();
                //Moram implementirati jos unique:
                    // dohvatiti sve svoje prijatelje
                    //if u listi postoji selektirani
                    //else dodaj
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
