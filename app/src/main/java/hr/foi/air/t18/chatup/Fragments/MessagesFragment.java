package hr.foi.air.t18.chatup.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import hr.foi.air.t18.chatup.Comparators.ConversationComparator;
import hr.foi.air.t18.chatup.Conversation.ConversationActivity;
import hr.foi.air.t18.chatup.Conversation.ConversationExpandableAdapter;
import hr.foi.air.t18.chatup.R;
import hr.foi.air.t18.core.Conversation;
import hr.foi.air.t18.core.MiddleMan;
import hr.foi.air.t18.core.SharedPreferencesClass;
import hr.foi.air.t18.core.User;
import hr.foi.air.t18.socketnotifications.SocketNotificationsManager;
import hr.foi.air.t18.webservice.ConversationAsync.FetchMessagesAsync;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.WebServiceResult;

/**
 * Simple Fragment where you can view messages
 * Created by Laptop on 9.11.2015..
 */
public class MessagesFragment extends Fragment
{
    private SocketNotificationsManager socketNotificationsManager;
    private ArrayList<Conversation> conversations;
    private HashMap<String, ArrayList<Conversation>> conversationMap;
    private ExpandableListView elv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.tab_fragment_messages, container, false);
        socketNotificationsManager = (SocketNotificationsManager) MiddleMan.getObject();
        conversations = new ArrayList<>();
        conversationMap = new HashMap<>();
        elv = (ExpandableListView) root.findViewById(R.id.conversation_expandable);

        addEvents();
        return root;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        User user = new User();
        user.setEmail(SharedPreferencesClass.getDefaults("UserEmail", getActivity().getApplicationContext()));
        user.setUsername(SharedPreferencesClass.getDefaults("UserUsername", getActivity().getApplicationContext()));

        FetchMessagesAsync fm = new FetchMessagesAsync(user, new IListener<ArrayList<Conversation>>()
        {
            @Override
            public void onBegin() {}

            @Override
            public void onFinish(WebServiceResult<ArrayList<Conversation>> result)
            {
                if (result.status == 0)
                {
                    conversations = result.data;
                    loadConversationsIntoListView();
                }
                else
                {
                    Toast.makeText(getContext(), result.message, Toast.LENGTH_LONG).show();
                }
            }
        });
        fm.execute();
    }

    private void loadConversationsIntoListView()
    {
        ArrayList<Conversation> singleConversations = new ArrayList<>();
        ArrayList<Conversation> groupConversations = new ArrayList<>();

        for (int i = 0; i < conversations.size(); i++)
        {
            int participantCount = conversations.get(i).getParticipants().size();
            if (participantCount > 2)
                groupConversations.add(conversations.get(i));
            else
                singleConversations.add(conversations.get(i));
        }

        Collections.sort(groupConversations, new ConversationComparator());
        Collections.sort(singleConversations, new ConversationComparator());

        conversationMap.put("Single conversations", singleConversations);
        conversationMap.put("Group conversations", groupConversations);
        elv.setAdapter(new ConversationExpandableAdapter(conversationMap, getActivity()));
        elv.expandGroup(1);
    }

    private void addEvents()
    {
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
                String key = (String) conversationMap.keySet().toArray()[groupPosition];
                Conversation conversation = conversationMap.get(key).get(childPosition);
                conversation.setSocketNotificationManager(socketNotificationsManager);
                MiddleMan.setObject(conversation);

                Intent intent = new Intent(getActivity(), ConversationActivity.class);
                startActivity(intent);
                return true;
            }
        });
    }

}
