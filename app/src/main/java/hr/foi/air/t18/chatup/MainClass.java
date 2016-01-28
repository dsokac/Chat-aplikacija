package hr.foi.air.t18.chatup;

import android.app.PendingIntent;
import android.app.ProgressDialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;

import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;


import java.util.HashMap;

import hr.foi.air.t18.chatup.Fragments.FragmentBuffer;
import hr.foi.air.t18.chatup.Fragments.HomePageFragment;
import hr.foi.air.t18.chatup.Fragments.MessagesFragment;
import hr.foi.air.t18.chatup.Fragments.SearchFragment;
import hr.foi.air.t18.chatup.Login.LoginActivity;

import hr.foi.air.t18.chatup.Menu.AboutUsDialog;
import hr.foi.air.t18.chatup.Menu.EditProfile;
import hr.foi.air.t18.chatup.Menu.Settings;

import hr.foi.air.t18.chatup.States.Black;
import hr.foi.air.t18.chatup.States.Blue;
import hr.foi.air.t18.chatup.States.Pink;
import hr.foi.air.t18.core.Conversation;
import hr.foi.air.t18.core.State.Context;
import hr.foi.air.t18.core.State.IState;
import hr.foi.air.t18.chatup.Notifications.CreateSocketConnectionAsync;
import hr.foi.air.t18.chatup.Notifications.FriendRequestNotifsAsync;
import hr.foi.air.t18.chatup.Notifications.NewMessageNotifsAsync;
import hr.foi.air.t18.socketnotifications.SocketEvents;
import hr.foi.air.t18.socketnotifications.SocketNotificationsManager;

import hr.foi.air.t18.core.ChatUpPreferences;
import hr.foi.air.t18.core.User;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.MenuAsync.LogoutAsync;
import hr.foi.air.t18.webservice.WebServiceResult;

public class MainClass extends AppCompatActivity {

    public static Toolbar toolbar_stgs;
    public static TabLayout tablayout_stgs;
    public static ViewPager viewpager_stgs;

    private SharedPreferences sharedPref;
    public String loggedIn;
    private ProgressDialog progress;
    private ViewPager viewPager;
    private PagerAdapter adapter;

    private SocketNotificationsManager snManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(ChatUpPreferences.getDefaults(getString(R.string.SettingsSearch), getApplicationContext()).equals(""))
        {
            ChatUpPreferences.setDefaults(getString(R.string.SettingsSearch), getString(R.string.SettingsRadioEmail), getApplicationContext());
        }

        if(ChatUpPreferences.getDefaults(getString(R.string.SettingsColor), getApplicationContext()).equals(""))
        {
            ChatUpPreferences.setDefaults(getString(R.string.SettingsColor), "0", getApplicationContext());
        }

        this.snManager = new SocketNotificationsManager("http://104.236.58.50:3000/", getApplicationContext());

        this.snManager.attachAsyncTasks(new CreateSocketConnectionAsync(this.snManager, null));

        new FriendRequestNotifsAsync().listenServer(this.snManager, SocketEvents.friendRequest,"Friend Request",R.drawable.logo_v1, null);
        new NewMessageNotifsAsync().listenServer(this.snManager, SocketEvents.newMessage, "New message!", R.drawable.logo_v1, null);

        setContentView(R.layout.activity_main);

        User user = new User();
        if (MiddleMan.getUserObject() != null) {
            user = (User) MiddleMan.getUserObject();
            loggedIn = user.getEmail();
            this.progress = new ProgressDialog(this);
        }

        this.sharedPref = this.getPreferences(MODE_PRIVATE);
        if (!this.sharedPref.contains("id") || (this.sharedPref.contains("id") && !this.sharedPref.getString("id", "unknown").equals(loggedIn))) {

            SharedPreferences.Editor editor = this.sharedPref.edit();
            editor.putString("id", this.loggedIn);
            editor.commit();
        } else {
            loggedIn = sharedPref.getString("id", "unknown");
        }

        Toast.makeText(this, loggedIn, Toast.LENGTH_LONG).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //for using in Settings
        toolbar_stgs = (Toolbar) this.findViewById(R.id.toolbar);
        viewpager_stgs = (ViewPager) this.findViewById(R.id.pager);
        tablayout_stgs = (TabLayout) this.findViewById(R.id.tab_layout);

        createTabs();
        MiddleMan.setNotificationObject(snManager);
    }

    /**
     * Private function for Logout user
     */
    private void Logout() {
        LogoutAsync logoutAsync = new LogoutAsync(loggedIn, new IListener<Void>() {

            /***
             * Overridden onBegin event of LogoutAsync task defines what is happening when async task starts to execute.
             * It displays message 'Signing in...'.
             */
            @Override
            public void onBegin() {
                progress.setMessage("Signing out...");
                progress.show();
            }


            /***
             * Overridden onFinish event of LogoutAsync task defines what happens when async task finish execution.
             * @param //status - status integer returned by web service
             * @param //message - message string returned by web service
             */
            @Override
            public void onFinish(WebServiceResult<Void> wsResult) {
                if (progress.isShowing()) progress.dismiss();

                Toast.makeText(getApplicationContext(), wsResult.message, Toast.LENGTH_LONG).show();
                if (wsResult.status == 0) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        logoutAsync.execute();
        this.snManager.stopBackgroundService();
    }

    //logic for creating menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //logic for choosing some item in menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_edit_profile:
                Intent i = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(i);
                break;
            case R.id.action_settings:
                Intent i2 = new Intent(getApplicationContext(), Settings.class);
                startActivity(i2);
                break;
            case R.id.action_home:
                viewPager.setCurrentItem(0);
                break;
            case R.id.action_about_us:
                AboutUsDialog aud = new AboutUsDialog(this);
                aud.show();
                break;
            case R.id.action_logout:
                Logout();
                break;
            case R.id.action_close_app:
                this.finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
        return true;
    }

    private void createTabs() {
        FragmentBuffer fragmentBuffer = new FragmentBuffer();
        fragmentBuffer.add(new HomePageFragment(), "Home Page");
        fragmentBuffer.add(new SearchFragment(), "Search");
        fragmentBuffer.add(new MessagesFragment(), "Message");

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        for (int i = 0; i < fragmentBuffer.count(); i++) {
            String tag = fragmentBuffer.getTag(i);
            tabLayout.addTab(tabLayout.newTab().setText(tag));
        }

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter(getSupportFragmentManager(), fragmentBuffer);

        // Here is ViewPager attached with an adapter
        viewPager.setAdapter(adapter);

        // Here is Android ViewPager attached to a page change listener of TabLayout
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Further the Android TabLayout is attached to a tab selected listener
        // here ViewPager’s page is set when a tab is selected
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        this.snManager.bindToService();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();

        HashMap<String,Object> hashElems = new HashMap<>();

        hashElems.clear();
        hashElems.put("toolbar_stgs", MainClass.toolbar_stgs);
        hashElems.put("tablayout_stgs",MainClass.tablayout_stgs);
        hashElems.put("viewpager_stgs",MainClass.viewpager_stgs);
      //  hashElems.put("elv",findViewById(R.id.conversation_expandable));

        hashElems.put("toolbar_settings",Settings.toolbar_settings);
        hashElems.put("relative_layout_stgs",Settings.relative_layout_stgs);
        hashElems.put("btnSettingsSave",Settings.btnSettingsSave);

        IState state = null;

        String settings_id = ChatUpPreferences.getDefaults(getString(R.string.SettingsColor), getApplicationContext());

        if (settings_id.equals("0")) {
            state = new Pink(hashElems);
        }

        else if (settings_id.equals("1")) {
            state = new Black(hashElems);
        }
        else if (settings_id.equals("2")) {
            state = new Blue(hashElems);
        }

        Context context = new Context();
        state.applyChange(context, null);
    }
}