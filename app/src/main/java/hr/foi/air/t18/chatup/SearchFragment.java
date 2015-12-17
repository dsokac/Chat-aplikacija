package hr.foi.air.t18.chatup;

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
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;

import org.json.JSONObject;
import java.util.ArrayList;
import hr.foi.air.t18.core.User;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.SearchAsync;
import hr.foi.air.t18.webservice.WebServiceResult;

/**
 * Created by Laptop on 9.11.2015..
 */
public class SearchFragment extends Fragment {

    private SharedPreferences sharedPref;
    private String loggedIn;
    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.tab_fragment_main, container, false);
        setRetainInstance(true);
        this.sharedPref = getActivity().getPreferences(getActivity().MODE_PRIVATE);
        this.loggedIn = sharedPref.getString("id","unknown");

        View root = inflater.inflate(R.layout.tab_fragment_search, container, false);
        final ArrayList<User> users = new ArrayList<User>();

        final View final_root = root;
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
                            users.add(new User(currentUser.getString("id"), currentUser.getString("id"), currentUser.getString("status")));
                        }
                        lv = (ListView) final_root.findViewById(R.id.searchListview);
                        lv.setAdapter(new UserListAdapter(getActivity(), users));
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.add_friends_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.add_friend_add:
                Toast.makeText(getActivity().getApplicationContext(), "Add", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.add_friend_cancel:
                Toast.makeText(getActivity().getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


}
