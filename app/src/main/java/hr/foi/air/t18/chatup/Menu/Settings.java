package hr.foi.air.t18.chatup.Menu;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import java.util.HashMap;

import hr.foi.air.t18.core.ChatUpPreferences;
import hr.foi.air.t18.state.Context;
import hr.foi.air.t18.chatup.R;


/**
 * Public class Settings implements logic of Settings activity
 */
public class Settings extends AppCompatActivity {
    private String settings_id;
    public static Toolbar toolbar_settings;
    public static RelativeLayout relative_layout_stgs;
    public static boolean created=false;
    public static Button btnSettingsSave;
    private HashMap<String,Object> hashElems = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        String searchOption = ChatUpPreferences.getDefaults(getString(R.string.SettingsSearch), getApplicationContext());
        if(searchOption.contentEquals(getString(R.string.SettingsRadioUsername)))
        {
            ((RadioButton)findViewById(R.id.SettingsRadioUsername)).setChecked(true);

        }

        String colorOption = ChatUpPreferences.getDefaults(getString(R.string.SettingsColor), getApplicationContext());
        if(colorOption.equalsIgnoreCase("0")) ((RadioButton)findViewById(R.id.radioDesign1)).setChecked(true);
        else if(colorOption.equalsIgnoreCase("1")) ((RadioButton)findViewById(R.id.radioDesign2)).setChecked(true);
        else if(colorOption.equalsIgnoreCase("2")) ((RadioButton)findViewById(R.id.radioDesign3)).setChecked(true);

        created=true;
        toolbar_settings = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_settings);
        relative_layout_stgs = (RelativeLayout) findViewById(R.id.main_layout);

        btnSettingsSave = (Button) findViewById(R.id.btnSettingsSave);
        final RadioGroup settings_radiogroup = (RadioGroup) findViewById(R.id.SettingsColor);
        final Context context = new Context();
        final RadioGroup search_radiogroup = (RadioGroup) findViewById(R.id.SettingsSearchRadioGroup);


        btnSettingsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioButtonID = settings_radiogroup.getCheckedRadioButtonId();
                View radioButton = settings_radiogroup.findViewById(radioButtonID);
                int idx = settings_radiogroup.indexOfChild(radioButton);
                settings_id = Integer.toString(idx);

                //Saves color settings
                String colorSettings_text = Integer.toString(getCheckedRadioButtonIndex(settings_radiogroup));
                ChatUpPreferences.setDefaults(getString(R.string.SettingsColor), colorSettings_text, getApplicationContext());

                //saves search settings
                String searchSettings_text = getCheckedRadioButtonText(search_radiogroup);
                ChatUpPreferences.setDefaults(getString(R.string.SettingsSearch), searchSettings_text, getApplicationContext());

                Settings.this.finish();
            }
        });
    }

    public int getCheckedRadioButtonIndex(RadioGroup radioGroup)
    {
        int radioID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioID);
        return radioGroup.indexOfChild(radioButton);
    }

    public String getCheckedRadioButtonText(RadioGroup radioGroup)
    {
        int radioID = radioGroup.getCheckedRadioButtonId();
        RadioButton radio = (RadioButton)radioGroup.findViewById(radioID);
        return radio.getText().toString();
    }
}