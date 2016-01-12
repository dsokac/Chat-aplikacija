package hr.foi.air.t18.chatup.States;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import hr.foi.air.t18.chatup.Fragments.SearchFragment;
import hr.foi.air.t18.chatup.MainClass;
import hr.foi.air.t18.chatup.Menu.EditProfile;
import hr.foi.air.t18.chatup.Menu.Settings;
import hr.foi.air.t18.chatup.R;
import hr.foi.air.t18.core.State.Context;
import hr.foi.air.t18.core.State.State;

/**
 * Created by JurmanLap on 11.1.2016..
 */
public class Green extends AppCompatActivity implements State {

    public void doAction(Context context) {
        Log.d("Odabir zelena", "");

        boja();

        context.setState(this);
    }

    private void boja (){

        MainClass.toolbar_stgs.setBackgroundResource(R.color.colorGreen);
        MainClass.tablayout_stgs.setBackgroundResource(R.color.colorGreen);
        MainClass.viewpager_stgs.setBackgroundResource(R.color.colorVeryLightGreen);

        //settings
        Settings.toolbar_settings.setBackgroundResource(R.color.colorGreen);
        Settings.relative_layout_stgs.setBackgroundResource(R.color.colorVeryLightGreen);
        SearchFragment.search_button.setVisibility(Button.VISIBLE);
        SearchFragment.search_button2.setVisibility(Button.INVISIBLE);

    }

}