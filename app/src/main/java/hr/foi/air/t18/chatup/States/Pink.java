package hr.foi.air.t18.chatup.States;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import hr.foi.air.t18.chatup.R;
import hr.foi.air.t18.core.State.Context;
import hr.foi.air.t18.core.State.IState;

/**
 * Created by JurmanLap on 11.1.2016..
 */
public class Pink extends AppCompatActivity implements IState {

    private HashMap<String,Object> elements;

    public Pink(HashMap<String, Object> elements)
    {
        this.elements = elements;
    }

    @Override
    public void applyChange(Context context, View view){

        ((Toolbar)elements.get("toolbar_stgs")).setBackgroundResource(R.color.colorPink);
        ((TabLayout)elements.get("tablayout_stgs")).setBackgroundResource(R.color.colorPink);
        ((ViewPager)elements.get("viewpager_stgs")).setBackgroundResource(R.color.colorLightPink);
//        ((TextView)elements.get("elv")).setBackgroundResource(R.color.colorPink);

  /*      ((Toolbar)elements.get("toolbar_settings")).setBackgroundResource(R.color.colorGreen);
        ((RelativeLayout)elements.get("relative_layout_stgs")).setBackgroundResource(R.color.colorVeryLightGreen);
        ((Button)elements.get("btnSettingsSave")).setBackgroundResource(R.drawable.button_shape3);+*/

        context.setState(this);
    }

    @Override
    public Object getData() {
        return null;
    }

}