package hr.foi.air.t18.chatup.MenuTests;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;

import com.robotium.solo.Solo;

import hr.foi.air.t18.chatup.Login.LoginActivity;


/**
 * Created by JurmanLap on 9.1.2016..
 */
public class SettingsRobotium extends ActivityInstrumentationTestCase2<LoginActivity> {
    Activity activity;
    private Solo solo;

    //konstruktor
    public SettingsRobotium() {
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
        solo.clearEditText(0);
        solo.clearEditText(1);
        solo.typeText(0, "mjurman@foi.hr");
        solo.typeText(1, "test");
        solo.clickOnButton(0);
        solo.waitForActivity(hr.foi.air.t18.chatup.MainClass.class, 100);
        solo.clickOnView(solo.getView(android.widget.ImageView.class, 0));
        solo.clickInList(2, 0);
        solo.waitForActivity(hr.foi.air.t18.chatup.Menu.Settings.class, 100);
        solo.clickOnView(solo.getView(hr.foi.air.t18.chatup.R.id.radioDesign1));
        solo.clickOnButton("Save");
        //solo.waitForActivity(hr.foi.air.t18.chatup.MainClass.class, 100);
        solo.clickOnView(solo.getView(android.widget.ImageView.class, 0));
        solo.clickInList(2, 0);
        solo.clickOnView(solo.getView(hr.foi.air.t18.chatup.R.id.radioDesign2));
        solo.clickOnButton("Save");
        //solo.waitForActivity(hr.foi.air.t18.chatup.MainClass.class, 100);
        solo.clickOnView(solo.getView(android.widget.ImageView.class, 0));
        solo.clickInList(2, 0);
        solo.clickOnButton("Save");
        solo.sleep(5000);
        solo.goBack();
    }
}