package hr.foi.air.t18.chatup;

import android.app.Activity;
import android.media.Image;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.robotium.solo.Solo;

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





