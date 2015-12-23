package hr.foi.air.t18.chatup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import hr.foi.air.t18.core.Conversation;
import hr.foi.air.t18.core.MiddleMan;
import hr.foi.air.t18.core.SharedPreferencesClass;
import hr.foi.air.t18.core.User;
import hr.foi.air.t18.webservice.FetchMessagesAsync;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.WebServiceResult;

/**
 * Simple Fragment where you can view messages
 * Created by Laptop on 9.11.2015..
 */
public class MessagesFragment extends Fragment
{
    private ArrayList<Conversation> conversations;
    private ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.tab_fragment_messages, container, false);

        conversations = new ArrayList<>();
        lv = (ListView) root.findViewById(R.id.single_conversationListView);

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

        addEvents();
        return root;
    }

    private void loadConversationsIntoListView()
    {
        lv.setAdapter(new ConversationListAdapter(conversations, getActivity()));
    }

    private void addEvents()
    {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getActivity(), ConversationActivity.class);
                MiddleMan.setObject(conversations.get(position));
                startActivity(intent);
            }
        });
    }
}
