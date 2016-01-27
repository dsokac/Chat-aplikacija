package hr.foi.air.t18.core.State;

import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.view.View;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JurmanLap on 11.1.2016..
 */
public interface IState {

    void applyChange(Context context, View view);

    Object getData();

}