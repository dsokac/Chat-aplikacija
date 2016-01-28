package hr.foi.air.t18.state;

import android.view.View;

/**
 * Created by JurmanLap on 11.1.2016..
 */
public interface IState {

    void applyChange(Context context, View view);

    Object getData();

}