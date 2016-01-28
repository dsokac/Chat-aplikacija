package hr.foi.air.t18.chatup.Conversation;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import hr.foi.air.t18.chatup.R;
import hr.foi.air.t18.chatup.UserListAdapter;
import hr.foi.air.t18.chatup.MiddleMan;
import hr.foi.air.t18.core.ChatUpPreferences;
import hr.foi.air.t18.core.User;
import hr.foi.air.t18.webservice.MainAsync.FriendsAsync;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.WebServiceResult;

/**
 * Activity used to add participant to one conversation.
 */
public class AddParticipantActivity extends AppCompatActivity
{
    private ListView lvFriends;
    private Button buttonOK;
    private Button buttonCancel;

    private View.OnClickListener listenerOK;
    private View.OnClickListener listenerCancel;
    private AdapterView.OnItemClickListener listenerFriends;

    private ArrayList<User> currentParticipants;
    private ArrayList<User> friends;

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeComponents();
        initializeEvents();
        bindEvents();
        loadCandidates();
    }

    /**
     * Inititalizes controls and objects.
     */
    private void initializeComponents()
    {
        activity = this;
        friends = new ArrayList<>();
        currentParticipants = (ArrayList<User>) MiddleMan.getObject();

        lvFriends = (ListView) findViewById(R.id.convAddParticipantList);
        buttonOK = (Button) findViewById(R.id.convAddParticipantOK);
        buttonCancel = (Button) findViewById(R.id.convAddParticipantCancel);
    }

    /**
     * Creates events for OK and Cancel buttons. Also marks the
     * selected would-be participants.
     */
    private void initializeEvents()
    {
        listenerOK = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (lvFriends.getCheckedItemCount() > 0)
                {
                    ArrayList<String> emailBuffer = new ArrayList<>();
                    SparseBooleanArray boolArray = lvFriends.getCheckedItemPositions();

                    for (int i = 0; i < friends.size(); i++)
                    {
                        if (boolArray.get(i))
                            emailBuffer.add(friends.get(i).getEmail());
                    }

                    MiddleMan.setObject(emailBuffer);

                    setResult(RESULT_OK);
                    finish();
                }
                else
                {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
                    dialogBuilder.setTitle(R.string.dialog_noselection_title);
                    dialogBuilder.setMessage(R.string.dialog_noselection_participants);
                    dialogBuilder.setPositiveButton("OK", null);
                    dialogBuilder.create().show();
                }
            }
        };

        listenerCancel = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setResult(RESULT_CANCELED);
                finish();
            }
        };

        listenerFriends = new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                SparseBooleanArray boolArray = lvFriends.getCheckedItemPositions();

                if (boolArray.get(position))
                    lvFriends.getChildAt(position)
                            .setBackgroundColor(Color.rgb(133, 211, 239));
                else
                    lvFriends.getChildAt(position)
                            .setBackgroundColor(Color.TRANSPARENT);
            }
        };
    }

    /**
     * Binds the created events to their designated controls.
     */
    private void bindEvents()
    {
        buttonOK.setOnClickListener(listenerOK);
        buttonCancel.setOnClickListener(listenerCancel);
        lvFriends.setOnItemClickListener(listenerFriends);
    }

    /**
     * Loads users that are possible candidates for being added to
     * the conversation. This method fetches all users from web
     * server and removes those candidates that are already
     * participating in the conversation.
     */
    private void loadCandidates()
    {
        String currentUserEmail = ChatUpPreferences.getDefaults("UserEmail", getApplicationContext());
        FriendsAsync f = new FriendsAsync(currentUserEmail, new IListener<JSONArray>()
        {
            @Override
            public void onBegin()
            {}

            @Override
            public void onFinish(WebServiceResult<JSONArray> result)
            {
                if (result.status == 0)
                {
                    try
                    {
                        for (int i = 0; i < result.data.length(); i++)
                        {
                            JSONObject jsonObject = result.data.getJSONObject(i);
                            User user = new User();
                            user.setEmail(jsonObject.getString("id"));
                            user.setUsername(jsonObject.getString("username"));
                            user.setStatus(jsonObject.getString("status"));
                            friends.add(user);
                        }
                        removeCurrentParticipants();
                        lvFriends.setAdapter(new UserListAdapter(activity, friends));
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), result.message, Toast.LENGTH_LONG).show();
            }
        });
        f.execute();
    }

    /**
     * Removes users that are already participating in
     * conversation.
     */
    private void removeCurrentParticipants()
    {
        ArrayList<User> removalList = new ArrayList<>();
        for (int i = 0; i < friends.size(); i++)
        {
            if (isParticipant(friends.get(i)))
                removalList.add(friends.get(i));
        }
        friends.removeAll(removalList);
    }

    /**
     * Checks if the user is already participating in
     * conversation.
     * @param user user that needs to be checked
     * @return True if the user is participating already, False otherwise
     */
    private boolean isParticipant(User user)
    {
        boolean participating = false;
        for (int i = 0; i < currentParticipants.size() && !participating; i++)
        {
            String participantEmail = currentParticipants.get(i).getEmail();
            if (user.getEmail().equals(participantEmail))
                participating = true;
        }

        return participating;
    }
}
