package hr.foi.air.t18.chatup.States;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import java.util.HashMap;
import hr.foi.air.t18.chatup.R;
import hr.foi.air.t18.core.State.Context;
import hr.foi.air.t18.core.State.IState;

/**
 * Created by JurmanLap on 11.1.2016..
 */
public class Blue implements IState {

    private HashMap<String,Object> elements;

    public Blue(HashMap<String, Object> elements)
    {
        this.elements = elements;
    }

    @Override
    public void applyChange(Context context,View view){

        ((Toolbar)elements.get("toolbar_stgs")).setBackgroundResource(R.color.colorPrimary);
        ((TabLayout)elements.get("tablayout_stgs")).setBackgroundResource(R.color.colorPrimary);
        ((ViewPager)elements.get("viewpager_stgs")).setBackgroundResource(R.color.colorWhite);

        ((Toolbar)elements.get("toolbar_settings")).setBackgroundResource(R.color.colorPrimary);
        ((RelativeLayout)elements.get("relative_layout_stgs")).setBackgroundResource(R.color.colorWhite);
        ((Button)elements.get("btnSettingsSave")).setBackgroundResource(R.drawable.button_shape);

        context.setState(this);
    }

    @Override
    public Object getData() {
        return null;
    }

}

