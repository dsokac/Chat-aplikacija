package hr.foi.air.t18.chatup.States;

import android.util.Log;
import android.widget.Button;
import android.widget.Toast;


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
public class Black implements State {

    public void doAction(Context context) {

        Log.d("Odabir crna", "");

        boja();
        searchTab();
        context.setState(this);
    }

    private void boja (){
        //fragments
        MainClass.toolbar_stgs.setBackgroundResource(R.color.colorBlack);
        MainClass.tablayout_stgs.setBackgroundResource(R.color.colorBlack);
        MainClass.viewpager_stgs.setBackgroundResource(R.color.colorLightWhite);

        //settings
        Settings.toolbar_settings.setBackgroundResource(R.color.colorBlack);
        Settings.relative_layout_stgs.setBackgroundResource(R.color.colorLightWhite);
        Settings.btnSettingsSave.setBackgroundResource(R.drawable.button_shape2);

    }

    private void searchTab (){
        SearchFragment.search_button.setVisibility(Button.INVISIBLE);
        SearchFragment.search_button2.setVisibility(Button.VISIBLE);
        SearchFragment.search_button2.setBackgroundResource(R.drawable.button_shape2);
        SearchFragment.search_text.setHint("Search by username");
    }
}

