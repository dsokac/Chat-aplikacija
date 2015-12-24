package hr.foi.air.t18.chatup;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import hr.foi.air.t18.core.Conversation;
import hr.foi.air.t18.core.MessageComparator;
import hr.foi.air.t18.core.Message;
import hr.foi.air.t18.core.MiddleMan;
import hr.foi.air.t18.core.SharedPreferencesClass;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.RefreshConversationAsync;
import hr.foi.air.t18.webservice.SendMessageAsync;
import hr.foi.air.t18.webservice.WebServiceResult;

public class ConversationActivity extends AppCompatActivity
{
    private Conversation conversation;
    private ListView lvMessages;
    private Button btnSendMessage;
    private EditText txtMessage;

    private Timer refreshTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        InitializeComponents();
        AddEvents();

        SortAndLoadMessagesIntoListView();
        lvMessages.setSelection(lvMessages.getCount() - 1);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        refreshTimer.cancel();
        refreshTimer.purge();
    }

    private void InitializeComponents()
    {
        conversation = (Conversation) MiddleMan.getObject();
        lvMessages = (ListView) findViewById(R.id.convMessages);
        btnSendMessage = (Button) findViewById(R.id.convSendButton);
        txtMessage = (EditText) findViewById(R.id.convTextBox);
    }

    private void AddEvents()
    {
        AddSendMessageEvent();
        AddRefreshEvent();
    }

    private void SortAndLoadMessagesIntoListView()
    {
        Collections.sort(conversation.getMessages(), new MessageComparator());
        lvMessages.setAdapter(new MessagesListAdapter(conversation.getMessages(), this));
    }

    private void AddSendMessageEvent()
    {
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;

                Message message = new Message(
                        txtMessage.getText().toString(),
                        SharedPreferencesClass.getDefaults("UserUsername", getApplicationContext()),
                        "",
                        "",
                        Message.TEXT
                );
                SendMessageAsync sm = new SendMessageAsync(conversation, message, new IListener<Message>() {
                    @Override
                    public void onBegin() {
                    }

                    @Override
                    public void onFinish(WebServiceResult<Message> result) {
                        if (result.status == 0) {
                            txtMessage.setText("");
                            conversation.addMessage(result.data);
                            SortAndLoadMessagesIntoListView();
                            lvMessages.setSelection(lvMessages.getCount() - 1);
                        } else
                            Toast.makeText(view.getContext(), result.message, Toast.LENGTH_LONG).show();
                    }
                });
                sm.execute();
            }
        });
    }

    private void AddRefreshEvent()
    {
        refreshTimer = new Timer();
        final Handler refreshHandler = new Handler();

        TimerTask refreshEvent = new TimerTask()
        {
            @Override
            public void run() {
                Runnable refreshThread = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        CallRefreshConversationAsyncTask();
                    }
                };
                refreshHandler.post(refreshThread);
            }
        };

        refreshTimer.schedule(refreshEvent, 1000, 5000);
    }

    private void CallRefreshConversationAsyncTask()
    {
        int numberOfMessages = conversation.getMessages().size();
        String lastTimestamp = conversation.getMessages().get(numberOfMessages - 1).getTimeSend();
        RefreshConversationAsync rc = new RefreshConversationAsync(
                conversation.getID(),
                lastTimestamp,
                new IListener<ArrayList<Message>>()
                {
                    @Override
                    public void onBegin() {}

                    @Override
                    public void onFinish(WebServiceResult<ArrayList<Message>> result)
                    {
                        if (result.status == 0 && result.data.size() > 0)
                        {
                            for (int i = 0; i < result.data.size(); i++)
                                conversation.addMessage(result.data.get(i));
                            SortAndLoadMessagesIntoListView();
                        }
                    }
                }
        );
        rc.execute();
    }
}