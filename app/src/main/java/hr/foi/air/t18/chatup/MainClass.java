package hr.foi.air.t18.chatup;

import android.content.Intent;
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

public class MainClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Home Page"));
        tabLayout.addTab(tabLayout.newTab().setText("Search"));
        tabLayout.addTab(tabLayout.newTab().setText("Messages"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
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

//        //Find reference to btn_register and subscribe to onClick event.
//        Button btnRegister = (Button)findViewById(R.id.btn_register);
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
//                startActivity(i);
//            }
//        });
//
//        Button btnLogin = (Button) findViewById(R.id.btn_login);
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(i);
//            }
//        });
//
//        Button bthHttpGET = (Button) findViewById(R.id.btn_HttpGET);
//        bthHttpGET.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new HttpGETAsyncTest().execute();
//            }
//        });
//
//        Button btnHttpPOST = (Button) findViewById(R.id.btn_HttpPOST);
//        btnHttpPOST.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new HttpPOSTAsyncTest().execute();
//            }
//        });
//    }
//
//    private class HttpGETAsyncTest extends AsyncTask<Void, Void, String>
//    {
//        @Override
//        protected String doInBackground(Void... params)
//        {
//            String response;
//            HashMap<String, String> testMap = new HashMap<String, String>();
//            testMap.put("Test1", "Danijel");
//            testMap.put("Test2", "Filipovic");
//            try {
//                HttpGET connection = new HttpGET("http://10.0.3.2:8080/Test");
//                connection.sendRequest(testMap);
//                response = connection.getResponse();
//            } catch(Exception e) {
//                response = e.getMessage();
//            }
//            return response;
//        }
//
//        @Override
//        protected void onPostExecute(String result)
//        {
//            super.onPostExecute(result);
//            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
//        }
//    }
//
//    private class HttpPOSTAsyncTest extends AsyncTask<Void, Void, String>
//    {
//
//        @Override
//        protected String doInBackground(Void... params)
//        {
//            String response;
//            HashMap<String, String> testMap = new HashMap<String, String>();
//            testMap.put("var1", "Danijel");
//            testMap.put("var2", "Filipovic");
//            try {
//                HttpPOST connection = new HttpPOST("http://10.0.3.2:8080/Test");
//                connection.sendRequest(testMap);
//                response = connection.getResponse();
//            } catch (Exception e) {
//                response = e.getMessage();
//            }
//            return response;
//        }
//
//        @Override
//        protected void onPostExecute(String result)
//        {
//            super.onPostExecute(result);
//            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
//        }
    }
    //menu
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
                break;
            case R.id.action_home:
                break;
            case R.id.action_about_us:
                AboutUsDialog aud = new AboutUsDialog(this);
                aud.show();
                break;
            case R.id.action_logout:
                break;
            case R.id.action_close_app:
                break;
        }
        return true;
    }
    //menu end
}