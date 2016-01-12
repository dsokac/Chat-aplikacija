package hr.foi.air.t18.chatup.States;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import hr.foi.air.t18.chatup.MainClass;
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

    }

}