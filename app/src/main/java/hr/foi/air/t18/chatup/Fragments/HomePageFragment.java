package hr.foi.air.t18.chatup.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import hr.foi.air.t18.chatup.ImagePickerActivity;
import hr.foi.air.t18.chatup.R;
import hr.foi.air.t18.chatup.UserListAdapter;
import hr.foi.air.t18.core.ChatUpPreferences;
import hr.foi.air.t18.core.User;
import hr.foi.air.t18.webservice.ConversationAsync.CreateConversationAsync;
import hr.foi.air.t18.webservice.MainAsync.FriendsAsync;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.WebServiceResult;

/**
 * Simple Fragment for Users Home Page
 * Created by Laptop on 9.11.2015..
 */
public class HomePageFragment extends Fragment {

    private ImageView profilePicture;
    private TextView usernameText;
    private SharedPreferences sharedPref;
    private String loggedIn;
    private ArrayList<User> friends;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.tab_fragment_main, container, false);
        //Log.d("Test refresha,"Refreshano");
        setRetainInstance(true);

        this.sharedPref = getActivity().getPreferences(getActivity().MODE_PRIVATE);
        this.loggedIn = sharedPref.getString("id", "unknown");
        View root = inflater.inflate(R.layout.tab_fragment_main, container, false);
        friends = new ArrayList<>();

        usernameText = (TextView) root.findViewById(R.id.profileUsername);
        profilePicture = (ImageView) root.findViewById(R.id.profilePicture);
        setProfilePicture();
        getSharedPreferencesData();

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

                        registerForContextMenu(lv);
                    } catch (Exception e) {
                    }
                } else {

                }
            }
        });

        getFriends.execute();

        return root;
    }

    private void getSharedPreferencesData() {
        if (!ChatUpPreferences.getDefaults("UserUsername", getActivity().getApplicationContext()).isEmpty()) {
            usernameText.setText(ChatUpPreferences.getDefaults("UserUsername", getActivity().getApplicationContext()));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setProfilePicture();
    }

    private void setProfilePicture() {
        String profilePictureInBase64 = ChatUpPreferences.getDefaults("UserProfilePictureBase64", getActivity().getApplicationContext());
        byte[] decodedByte = Base64.decode(profilePictureInBase64, Base64.NO_WRAP | Base64.URL_SAFE);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        profilePicture.setImageBitmap(bitmap);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(R.string.friend_menu);
        menu.add(0, v.getId(), 0, R.string.conv_start);
        menu.add(0, v.getId(), 0, R.string.add_friend_cancel);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        boolean returnValue = true;
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getTitle().equals(getString(R.string.conv_start)))
        {
            createNewConversation(info.position);
        }
        else
            returnValue = false;

        return returnValue;
    }

    /**
     * Creates new conversation with the user on given index
     * from the list of users.
     * @param index position of user in the list of friends
     */
    private void createNewConversation(int index)
    {
        String email1 = ChatUpPreferences.getDefaults("UserEmail", getActivity().getApplicationContext());
        String email2 = friends.get(index).getEmail();

        CreateConversationAsync cc = new CreateConversationAsync(email1, email2, new IListener<Void>()
        {
            @Override
            public void onBegin() {}

            @Override
            public void onFinish(WebServiceResult<Void> result)
            {
            }
        });

        cc.execute();
    }
}
