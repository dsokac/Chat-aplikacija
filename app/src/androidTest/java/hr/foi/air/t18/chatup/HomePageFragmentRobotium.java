package hr.foi.air.t18.chatup;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.ImageView;

import com.robotium.solo.Solo;

import hr.foi.air.t18.chatup.Login.LoginActivity;

/**
 * Created by JurmanLap on 9.1.2016..
 */
    public class HomePageFragmentRobotium extends ActivityInstrumentationTestCase2<LoginActivity> {
        Activity activity;
        private Solo solo;

        //konstruktor
        public HomePageFragmentRobotium() {
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
            //testing friend list
            solo.clickInList(1);
            solo.clickLongInList(2);
            solo.clickOnMenuItem("Cancel");
            solo.waitForActivity(hr.foi.air.t18.chatup.MainClass.class, 100);
            solo.clickLongInList(1);
            solo.clickOnMenuItem("Start conversation");
            solo.waitForActivity(hr.foi.air.t18.chatup.MainClass.class, 100);
            //testing image
            ImageView imageButton = (ImageView) solo.getView(R.id.profilePicture);
            solo.clickLongOnView(imageButton);
            solo.goBack();
            /// /exit
            solo.goBack();
        }

    }





