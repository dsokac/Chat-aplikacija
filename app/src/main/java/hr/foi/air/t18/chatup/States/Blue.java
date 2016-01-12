package hr.foi.air.t18.chatup.States;

import android.util.Log;
import android.widget.Button;

import hr.foi.air.t18.chatup.Fragments.SearchFragment;
import hr.foi.air.t18.chatup.Menu.EditProfile;
import hr.foi.air.t18.chatup.Menu.Settings;
import hr.foi.air.t18.chatup.R;
import hr.foi.air.t18.chatup.MainClass;
import hr.foi.air.t18.core.State.Context;
import hr.foi.air.t18.core.State.State;

/**
 * Created by JurmanLap on 11.1.2016..
 */
public class Blue implements State {

    public void doAction(Context context) {

        Log.d("Odabir plava", "");

        boja();

        context.setState(this);
    }



    private void boja(){
        MainClass.toolbar_stgs.setBackgroundResource(R.color.colorPrimary);
        MainClass.tablayout_stgs.setBackgroundResource(R.color.colorPrimary);
        MainClass.viewpager_stgs.setBackgroundResource(R.color.colorWhite);

        //settings
        Settings.toolbar_settings.setBackgroundResource(R.color.colorPrimary);
        Settings.relative_layout_stgs.setBackgroundResource(R.color.colorWhite);

        SearchFragment.search_button.setVisibility(Button.VISIBLE);
        SearchFragment.search_button2.setVisibility(Button.VISIBLE);
    }
}

