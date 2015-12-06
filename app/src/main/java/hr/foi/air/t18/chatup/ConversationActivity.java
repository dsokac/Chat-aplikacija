package hr.foi.air.t18.chatup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.foi.air.t18.core.Conversation;
import hr.foi.air.t18.core.IMessage;
import hr.foi.air.t18.core.MessageComparator;
import hr.foi.air.t18.core.MessageText;
import hr.foi.air.t18.core.MiddleMan;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.SendMessageAsync;
import hr.foi.air.t18.webservice.WebServiceResult;

public class ConversationActivity extends AppCompatActivity
{
    private Conversation conversation;
    private ListView lvMessages;
    private Button btnSendMessage;
    private EditText txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        conversation = (Conversation) MiddleMan.getObject();
        lvMessages = (ListView) findViewById(R.id.convMessages);
        btnSendMessage = (Button) findViewById(R.id.convSendButton);
        txtMessage = (EditText) findViewById(R.id.convTextBox);

        AddEvents();
        SortAndLoadMessagesIntoListView();
    }

    private void AddEvents()
    {
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                IMessage message = new MessageText(txtMessage.getText().toString(), "mirko", "", "");

                SendMessageAsync sm = new SendMessageAsync(conversation, message, new IListener<Void>() {
                    @Override
                    public void onBegin() {}

                    @Override
                    public void onFinish(WebServiceResult<Void> result)
                    {
                        Toast.makeText(getApplicationContext(), result.message, Toast.LENGTH_LONG).show();
                    }
                });
                sm.execute();
            }
        });
    }

    private void SortAndLoadMessagesIntoListView()
    {
        Collections.sort(conversation.getMessages(), new MessageComparator());
        lvMessages.setAdapter(new MessagesListAdapter(conversation.getMessages(), this));
    }
}
