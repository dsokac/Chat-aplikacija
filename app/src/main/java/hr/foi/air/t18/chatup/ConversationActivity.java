package hr.foi.air.t18.chatup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air.t18.core.Conversation;
import hr.foi.air.t18.core.IMessage;
import hr.foi.air.t18.core.MiddleMan;

public class ConversationActivity extends AppCompatActivity
{
    private ArrayList<IMessage> messages;
    private ListView lvMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        messages = (ArrayList<IMessage>) MiddleMan.getObject();
        lvMessages = (ListView) findViewById(R.id.convMessages);

        loadMessagesIntoListView();
    }

    private void loadMessagesIntoListView()
    {
        lvMessages.setAdapter(new MessagesListAdapter(messages, this));
    }
}
