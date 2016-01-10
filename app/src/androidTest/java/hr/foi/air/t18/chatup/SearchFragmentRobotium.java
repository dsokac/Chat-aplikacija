package hr.foi.air.t18.chatup;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Point;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;
import android.view.Display;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.robotium.solo.Solo;

/**
 * Created by JurmanLap on 10.1.2016..
 */
public class SearchFragmentRobotium extends ActivityInstrumentationTestCase2<LoginActivity> {
    Activity activity;
    private Solo solo;

    //konstruktor
    public SearchFragmentRobotium() {
        super (LoginActivity.class);
    }

    @Override
    protected void setUp() throws  Exception {

        super.setUp();
        solo=new Solo (getInstrumentation());
        activity = getActivity();
    }


    @Override protected void tearDown() throws  Exception {
        solo.finishOpenedActivities();
        activity.finish();
        super.tearDown();
    }

    @MediumTest
    public void testWithRobotium(){
        //login
        solo.clickOnButton(0);
        solo.waitForActivity(hr.foi.air.t18.chatup.MainClass.class, 100);
        //testovi...
        solo.clickLongInList(2); //samo test

    }

}
