package hr.foi.air.t18.chatup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collections;

import hr.foi.air.t18.core.Conversation;
import hr.foi.air.t18.core.MessageComparator;
import hr.foi.air.t18.core.Message;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        conversation = (Conversation) MiddleMan.getObject();
        lvMessages = (ListView) findViewById(R.id.convMessages);
        btnSendMessage = (Button) findViewById(R.id.convSendButton);
        txtMessage = (EditText) findViewById(R.id.convTextBox);

        AddEvents();
        SortAndLoadMessagesIntoListView();
        lvMessages.setSelection(lvMessages.getCount() - 1);
    }

    private void AddEvents()
    {
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final View view = v;
                Message message = new Message(txtMessage.getText().toString(), "mirko", "", "");
                SendMessageAsync sm = new SendMessageAsync(conversation, message, new IListener<Message>() {
                    @Override
                    public void onBegin() {}

                    @Override
                    public void onFinish(WebServiceResult<Message> result)
                    {
                        if (result.status == 0)
                        {
                            txtMessage.setText("");
                            conversation.addMessage(result.data);
                            SortAndLoadMessagesIntoListView();
                            lvMessages.setSelection(lvMessages.getCount() - 1);
                        }
                        else
                            Toast.makeText(view.getContext(), result.message, Toast.LENGTH_LONG).show();
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