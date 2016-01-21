package hr.foi.air.t18.chatup.Conversation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import hr.foi.air.t18.chatup.R;
import hr.foi.air.t18.chatup.Ads.DlAdsListener;
import hr.foi.air.t18.core.Conversation;
import hr.foi.air.t18.core.Message;
import hr.foi.air.t18.chatup.Comparators.MessageComparator;
import hr.foi.air.t18.core.MiddleMan;
import hr.foi.air.t18.core.SharedPreferencesClass;
import hr.foi.air.t18.webservice.ConversationAsync.AddParticipantsToConversationAsyncTask;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.ConversationAsync.RefreshConversationAsync;
import hr.foi.air.t18.webservice.ConversationAsync.SendMessageAsync;
import hr.foi.air.t18.webservice.WebServiceResult;

/**
 * Activity where participants exchange messages.
 */
public class ConversationActivity extends AppCompatActivity
{
    private InterstitialAd mInterstitial;
    private static final String AD_UNIT_ID = "ca-app-pub-8639732656343372/9330745843";
    private int adds_counter=0;

    private Conversation conversation;
    private ListView lvMessages;
    private Button btnSendMessage;
    private EditText txtMessage;

    private Timer refreshTimer;
    private Activity activity;
    private int requestCode = 0x01;

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

        //za sad se poziva samo prilikom otvaranja poruke
        Adds();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        refreshTimer.cancel();
        refreshTimer.purge();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.conversation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int itemID = item.getItemId();
        if (itemID == R.id.action_add_participant)
        {
            Intent i = new Intent(this, AddParticipantActivity.class);
            MiddleMan.setObject(conversation.getParticipants());
            startActivityForResult(i, requestCode);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            ArrayList<String> emailBuffer =
                    (ArrayList<String>) MiddleMan.getObject();

            AddParticipantsToConversationAsyncTask aptc =
                    new AddParticipantsToConversationAsyncTask(conversation.getID(), emailBuffer, new IListener<ArrayList<String>>()
                    {
                        @Override
                        public void onBegin()
                        {}

                        @Override
                        public void onFinish(WebServiceResult<ArrayList<String>> result)
                        {
                            if (result.status != 0)
                            {
                                Toast.makeText(activity, result.message, Toast.LENGTH_LONG).show();
                            }
                        }
                    });

            aptc.execute();
        }
    }

    /**
     * Initializes controls used in the Activity.
     */
    private void InitializeComponents()
    {
        activity = this;

        conversation = (Conversation) MiddleMan.getObject();
        lvMessages = (ListView) findViewById(R.id.convMessages);
        btnSendMessage = (Button) findViewById(R.id.convSendButton);
        txtMessage = (EditText) findViewById(R.id.convTextBox);
    }



    /**
     * Registers events.
     */
    private void AddEvents()
    {
        AddSendMessageEvent();
        AddRefreshEvent();

    }

    /**
     * Sorts the messages and displays them on screen.
     */
    private void SortAndLoadMessagesIntoListView()
    {
        Collections.sort(conversation.getMessages(), new MessageComparator());
        lvMessages.setAdapter(new MessagesListAdapter(conversation.getMessages(), this));
    }

    /**
     * Adds an event that happens when the user presses the SEND button.
     */
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

    /**
     * Adds an event that refreshes the list of messages every 5 seconds.
     */
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

    //Adds function for loading an fullscreen Add
    private void Adds()
    {
        mInterstitial = new InterstitialAd(ConversationActivity.this);
        mInterstitial.setAdUnitId(AD_UNIT_ID);
        mInterstitial.setAdListener(new DlAdsListener(ConversationActivity.this) {

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitial.show();
            }
        });

        mInterstitial.loadAd(new com.google.android.gms.ads.AdRequest.Builder().build());
    }

    /**
     * Fetches the new messages. Used in refresh event.
     */
    private void CallRefreshConversationAsyncTask()
    {
        int numberOfMessages = conversation.getMessages().size();
        if (numberOfMessages > 0)
        {
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
}