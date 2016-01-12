package hr.foi.air.t18.chatup.States;

import android.util.Log;
import android.widget.Toast;

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
        context.setState(this);

    }

    private void boja (){

        MainClass.toolbar_stgs.setBackgroundResource(R.color.colorBlack);
        MainClass.tablayout_stgs.setBackgroundResource(R.color.colorBlack);
        MainClass.viewpager_stgs.setBackgroundResource(R.color.colorLightWhite);

    }

}

