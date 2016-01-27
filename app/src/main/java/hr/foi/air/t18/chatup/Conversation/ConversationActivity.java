package hr.foi.air.t18.chatup.Conversation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import hr.foi.air.t18.chatup.R;
import hr.foi.air.t18.chatup.Ads.DlAdsListener;
import hr.foi.air.t18.core.Conversation;
import hr.foi.air.t18.core.Message;
import hr.foi.air.t18.chatup.Comparators.MessageComparator;
import hr.foi.air.t18.core.MiddleMan;
import hr.foi.air.t18.core.SharedPreferencesClass;
import hr.foi.air.t18.core.User;
import hr.foi.air.t18.socketnotifications.NewMessageNotifsAsync;
import hr.foi.air.t18.socketnotifications.SocketNotificationsManager;
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

    private SocketNotificationsManager socketNotificationManager;
    private InterstitialAd mInterstitial;
    private static final String AD_UNIT_ID = "ca-app-pub-8639732656343372/9330745843";
    private int adds_counter=0;

    private Conversation conversation;
    private ListView lvMessages;
    private Button btnSendMessage;
    private Button btnSendPicture;
    private EditText txtMessage;

    private Timer refreshTimer;
    private Activity activity;

    private int REQUEST_ADDPARTICIPANT = 0x00000001;
    private int REQUEST_CHOOSEIMAGE = 0x00000002;

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
            startActivityForResult(i, REQUEST_ADDPARTICIPANT);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_ADDPARTICIPANT && resultCode == RESULT_OK)
            AddParticipants();
        else if (requestCode == REQUEST_CHOOSEIMAGE && resultCode == RESULT_OK && data != null)
            SendPicture(data.getData());
    }

    /**
     * Initializes controls used in the Activity.
     */
    private void InitializeComponents()
    {
        activity = this;

        conversation = (Conversation) MiddleMan.getObject();
        socketNotificationManager = conversation.getSocketNotificationManager();
        lvMessages = (ListView) findViewById(R.id.convMessages);
        btnSendMessage = (Button) findViewById(R.id.convSendButton);
        btnSendPicture = (Button) findViewById(R.id.convPictureButton);
        txtMessage = (EditText) findViewById(R.id.convTextBox);
    }

    /**
     * Registers events.
     */
    private void AddEvents()
    {
        AddSendMessageEvent();
        AddSendPictureEvent();
        AddRefreshEvent();
    }

    private void AddParticipants()
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
                        Toast.makeText(activity, result.message, Toast.LENGTH_LONG).show();
                }
            });

        aptc.execute();
    }

    private void SendPicture(Uri imageUri)
    {
        try
        {
            InputStream imageStream = getContentResolver().openInputStream(imageUri);
            Bitmap image = BitmapFactory.decodeStream(imageStream);

            image = ResizeImage(image);

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            String imageBase64 = Base64.encodeToString(bytes.toByteArray(), Base64.NO_WRAP | Base64.URL_SAFE);

            Message message = new Message(
                    imageBase64,
                    SharedPreferencesClass.getDefaults("UserUsername", getApplicationContext()),
                    "", "",
                    Message.IMAGE
            );

            SendMessageAsync sm = new SendMessageAsync(conversation, message, new IListener<Message>() {
                @Override
                public void onBegin() {}

                @Override
                public void onFinish(WebServiceResult<Message> result)
                {
                    if (result.status == 0)
                    {
                        txtMessage.setText("");
                        AddMessageToConversation(result.data);
                        SortAndLoadMessagesIntoListView();


                        JSONObject object = new JSONObject();
                        try {
                            String sender = SharedPreferencesClass.getDefaults("UserEmail", getApplicationContext());
                            JSONArray usersInConversation = new JSONArray();
                            int i = 0;
                            for(User user : conversation.getParticipants()) {
                                if(!(user.getEmail().equalsIgnoreCase(sender))) {
                                    usersInConversation.put(user.getEmail());
                                    i++;
                                }
                            }
                            object.put("sender", sender);
                            object.put("participants", usersInConversation);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        socketNotificationManager.attachAsyncTasks(new NewMessageNotifsAsync(socketNotificationManager, object));
                    } else
                        Toast.makeText(activity, result.message, Toast.LENGTH_LONG).show();
                }
            });
            sm.execute();
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Bitmap ResizeImage(Bitmap image)
    {
        float aspectRatio = image.getWidth() / image.getHeight();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        if (image.getWidth() > size.x)
        {
            int width = size.x;
            int height = Math.round(width / aspectRatio);

            return Bitmap.createScaledBitmap(image, width, height, false);
        }
        else
            return image;
    }

    /**
     * Sorts the messages and displays them on screen.
     */
    private void SortAndLoadMessagesIntoListView()
    {
        Collections.sort(conversation.getMessages(), new MessageComparator());
        lvMessages.setAdapter(new MessagesListAdapter(conversation.getMessages(), this));

        if (lvMessages.getCount() > 0)
            lvMessages.setSelection(lvMessages.getCount() - 1);
    }

    /**
     * Adds an event that happens when the user presses the SEND button.
     */
    private void AddSendMessageEvent()
    {
        btnSendMessage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final View view = v;

                Message message = new Message(
                        txtMessage.getText().toString(),
                        SharedPreferencesClass.getDefaults("UserUsername", getApplicationContext()),
                        "", "",
                        Message.TEXT
                );

                //It checks if message contains any letters, and if it is empty it shows
                //a alert dialog with message and terminate proces to avoid error message
                //from server.
                if(message.getContent().contentEquals(""))
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity)
                            .setTitle(R.string.ConversationDialogTitle)
                            .setMessage(R.string.ConversationDialogContent)
                            .setNeutralButton(R.string.ConversationDialogButtonText, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    alertDialog.show();
                    return;
                }

                SendMessageAsync sm = new SendMessageAsync(conversation, message, new IListener<Message>() {
                    @Override
                    public void onBegin() {
                    }

                    @Override
                    public void onFinish(WebServiceResult<Message> result) {
                        if (result.status == 0)
                        {
                            txtMessage.setText("");
                            AddMessageToConversation(result.data);
                            SortAndLoadMessagesIntoListView();

                            JSONObject object = new JSONObject();
                            try {
                                String sender = SharedPreferencesClass.getDefaults("UserEmail", getApplicationContext());
                                JSONArray usersInConversation = new JSONArray();
                                int i = 0;
                                for(User user : conversation.getParticipants()) {
                                    if(!(user.getEmail().equalsIgnoreCase(sender))) {
                                        usersInConversation.put(user.getEmail());
                                        i++;
                                    }
                                }
                                object.put("sender", sender);
                                object.put("participants", usersInConversation);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            socketNotificationManager.attachAsyncTasks(new NewMessageNotifsAsync(socketNotificationManager, object));
                        } else
                            Toast.makeText(view.getContext(), result.message, Toast.LENGTH_LONG).show();
                    }
                });
                sm.execute();
            }
        });
    }

    private void AddSendPictureEvent()
    {
        btnSendPicture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent imagePicker = new Intent(Intent.ACTION_PICK);
                imagePicker.setType("image/*");
                startActivityForResult(imagePicker, REQUEST_CHOOSEIMAGE);
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
                                    AddMessageToConversation(result.data.get(i));
                                SortAndLoadMessagesIntoListView();
                            }
                        }
                    }
            );
            rc.execute();
        }
    }

    private void AddMessageToConversation(Message message)
    {
        boolean messageExists = conversation.getMessages().contains(message);
        if (!messageExists)
            conversation.addMessage(message);
    }
}