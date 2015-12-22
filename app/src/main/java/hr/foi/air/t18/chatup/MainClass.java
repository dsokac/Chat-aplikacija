package hr.foi.air.t18.chatup;

import android.app.ProgressDialog;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import hr.foi.air.t18.core.HttpGET;
import hr.foi.air.t18.core.HttpPOST;
import hr.foi.air.t18.core.MiddleMan;
import hr.foi.air.t18.core.User;
import hr.foi.air.t18.webservice.IListener;
import hr.foi.air.t18.webservice.LoginAsync;
import hr.foi.air.t18.webservice.LogoutAsync;
import hr.foi.air.t18.webservice.WebServiceResult;

public class MainClass extends AppCompatActivity {

    private SharedPreferences sharedPref;
    public String loggedIn;
    private ProgressDialog progress;
    private ViewPager viewPager;
    private PagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (MiddleMan.getObject() != null) {
            User user = (User) MiddleMan.getObject();
            loggedIn = user.getEmail();
            this.progress = new ProgressDialog(this);
        }

        this.sharedPref = this.getPreferences(this.MODE_PRIVATE);
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

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Home Page"));
        tabLayout.addTab(tabLayout.newTab().setText("Search"));
        tabLayout.addTab(tabLayout.newTab().setText("Messages"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());

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
    }

    private void Logout()
    {
        LogoutAsync logoutAsync = new LogoutAsync(loggedIn, new IListener<Void>() {
            /***
             * Overridden onBegin event of LoginAsync task defines what is happening when async task starts to execute.
             * It displays message 'Signing in...'.
             */
            @Override
            public void onBegin() {
                progress.setMessage("Signing out...");
                progress.show();
            }

            @Override
            public void onFinish(WebServiceResult<Void> wsResult) {
                if(progress.isShowing()) progress.dismiss();

                Toast.makeText(getApplicationContext(), wsResult.message, Toast.LENGTH_LONG).show();
                if(wsResult.status == 0)
                {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        logoutAsync.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_edit_profile:
                Intent i = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(i);
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
    //menu end
}