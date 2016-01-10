package hr.foi.air.t18.chatup.Conversation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import hr.foi.air.t18.chatup.R;
import hr.foi.air.t18.chatup.UserListAdapter;
import hr.foi.air.t18.core.MiddleMan;
import hr.foi.air.t18.core.SharedPreferencesClass;
import hr.foi.air.t18.core.User;
import hr.foi.air.t18.webservice.FriendsAsync;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.WebServiceResult;

/**
 * Created by Danijel on 9.1.2016..
 */
public class AddParticipantActivity extends AppCompatActivity
{
    private ListView lvFriends;
    private Button buttonOK;
    private Button buttonCancel;

    private View.OnClickListener listenerOK;
    private View.OnClickListener listenerCancel;

    private ArrayList<User> currentParticipants;
    private ArrayList<User> friends;

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);

        initializeComponents();
        initializeEvents();
        bindEvents();
        loadCandidates();
    }

    private void initializeComponents()
    {
        activity = this;
        friends = new ArrayList<>();
        currentParticipants = (ArrayList<User>) MiddleMan.getObject();

        lvFriends = (ListView) findViewById(R.id.convAddParticipantList);
        buttonOK = (Button) findViewById(R.id.convAddParticipantOK);
        buttonCancel = (Button) findViewById(R.id.convAddParticipantCancel);
    }

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
    }

    private void bindEvents()
    {
        buttonOK.setOnClickListener(listenerOK);
        buttonCancel.setOnClickListener(listenerCancel);
    }

    private void loadCandidates()
    {
        String currentUserEmail = SharedPreferencesClass.getDefaults("UserEmail", getApplicationContext());
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
