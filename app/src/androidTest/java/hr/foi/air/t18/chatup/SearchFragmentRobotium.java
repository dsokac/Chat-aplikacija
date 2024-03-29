package hr.foi.air.t18.chatup;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;

import com.robotium.solo.Solo;

import hr.foi.air.t18.chatup.Login.LoginActivity;

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
        solo.clearEditText(0);
        solo.clearEditText(1);
        solo.typeText(0, "mjurman@foi.hr");
        solo.typeText(1, "test");
        solo.clickOnButton(0);
        solo.waitForActivity(hr.foi.air.t18.chatup.MainClass.class, 100);
        //click on Search
        solo.clickOnText("Search");
        //enter matija in Search
        solo.enterText((android.widget.EditText) solo.getView(hr.foi.air.t18.chatup.R.id.searchUser), "matija");
        solo.clickOnView(solo.getView(hr.foi.air.t18.chatup.R.id.searchButton));
        //long click on matija
        solo.clickLongOnView(solo.getView(hr.foi.air.t18.chatup.R.id.searchListview));;
        solo.clickOnMenuItem("Cancel");

    }
}
