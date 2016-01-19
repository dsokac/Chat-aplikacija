package hr.foi.air.t18.chatup.States;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import java.util.HashMap;
import hr.foi.air.t18.chatup.R;
import hr.foi.air.t18.core.State.Context;
import hr.foi.air.t18.core.State.IState;

/**
 * Created by JurmanLap on 11.1.2016..
 */
public class Green extends AppCompatActivity implements IState {

    private HashMap<String,Object> elements;

    public Green(HashMap<String, Object> elements)
    {
        this.elements = elements;
    }

    public void paint(Context context) {
        Log.d("Odabir zelena", "");
        setColor();
        //searchTab();
        context.setState(this);
    }

    @Override
    public void setColor(){

        ((Toolbar)elements.get("toolbar_stgs")).setBackgroundResource(R.color.colorGreen);
        ((TabLayout)elements.get("tablayout_stgs")).setBackgroundResource(R.color.colorGreen);
        ((ViewPager)elements.get("viewpager_stgs")).setBackgroundResource(R.color.colorVeryLightGreen);

        ((Toolbar)elements.get("toolbar_settings")).setBackgroundResource(R.color.colorGreen);
        ((RelativeLayout)elements.get("relative_layout_stgs")).setBackgroundResource(R.color.colorVeryLightGreen);
        ((Button)elements.get("btnSettingsSave")).setBackgroundResource(R.drawable.button_shape3);
    }


/*
    private void searchTab (){
        SearchFragment.search_button.setVisibility(Button.VISIBLE);
        SearchFragment.search_button2.setVisibility(Button.INVISIBLE);
        SearchFragment.search_button.setBackgroundResource(R.drawable.button_shape3);
        SearchFragment.search_text.setHint("Search by email");
    }
*/
}