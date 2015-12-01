package hr.foi.air.t18.chatup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import hr.foi.air.t18.core.Conversation;
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

        conversations = loadTestData();
        lv = (ListView) root.findViewById(R.id.single_conversationListView);
        loadConversationsIntoListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user1 = new User();
                User user2 = new User();
                user1.setEmail("darko@mail.hr");
                user2.setEmail("mirko@mail.hr");
                FetchMessagesAsync fm = new FetchMessagesAsync(user1, user2, new IListener<ArrayList<Conversation>>() {
                    @Override
                    public void onBegin() {}

                    @Override
                    public void onFinish(WebServiceResult<ArrayList<Conversation>> result) {
                        Toast.makeText(getContext(), result.message, Toast.LENGTH_LONG).show();
                    }
                });
                fm.execute();
            }
        });

        return root;
    }

    private void loadConversationsIntoListView()
    {
        lv.setAdapter(new ConversationListAdapter(conversations, getActivity()));
    }

    private ArrayList<Conversation> loadTestData()
    {
        ArrayList<Conversation> c = new ArrayList<Conversation>();

        User u1 = new User("jovan@mail.hr", "jjovan", 'm', "12.18.1991.");
        User u2 = new User("anabela@mail.hr", "AnnaBella", 'z', "01.11.1992.");
        User u3 = new User("tovar@mail.hr", "Tovar", 'm', "12.12.1995.");

        Conversation c1 = new Conversation();
        Conversation c2 = new Conversation();
        Conversation c3 = new Conversation();

        c1.addParticipant(u1);
        c1.addParticipant(u2);
        c2.addParticipant(u2);
        c2.addParticipant(u3);
        c3.addParticipant(u3);
        c3.addParticipant(u1);

        c.add(c1);
        c.add(c2);
        c.add(c3);

        return c;
    }
}
