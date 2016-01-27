package hr.foi.air.t18.chatup.Menu;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.util.HashMap;
import hr.foi.air.t18.chatup.MainClass;
import hr.foi.air.t18.core.SharedPreferencesClass;
import hr.foi.air.t18.core.State.Context;
import hr.foi.air.t18.chatup.R;
import hr.foi.air.t18.chatup.States.Black;
import hr.foi.air.t18.chatup.States.Green;
import hr.foi.air.t18.chatup.States.Blue;
import hr.foi.air.t18.core.State.IState;


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

        String searchOption = SharedPreferencesClass.getDefaults(getString(R.string.SettingsSearch),getApplicationContext());
        if(searchOption.contentEquals(getString(R.string.SettingsRadioUsername)))
        {
            ((RadioButton)findViewById(R.id.SettingsRadioUsername)).setChecked(true);

        }

        String colorOption = SharedPreferencesClass.getDefaults(getString(R.string.SettingsColor), getApplicationContext());
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

                String colorSettings_text = Integer.toString(getCheckedRadioButtonIndex(settings_radiogroup));
                SharedPreferencesClass.setDefaults(getString(R.string.SettingsColor),colorSettings_text,getApplicationContext());

                String searchSettings_text = getCheckedRadioButtonText(search_radiogroup);
                SharedPreferencesClass.setDefaults(getString(R.string.SettingsSearch),searchSettings_text,getApplicationContext());

                hashElems.clear();
                hashElems.put("toolbar_stgs", MainClass.toolbar_stgs);
                hashElems.put("tablayout_stgs",MainClass.tablayout_stgs);
                hashElems.put("viewpager_stgs",MainClass.viewpager_stgs);
                hashElems.put("toolbar_settings",Settings.toolbar_settings);
                hashElems.put("relative_layout_stgs",Settings.relative_layout_stgs);
                hashElems.put("btnSettingsSave",Settings.btnSettingsSave);

                IState state = null;

                if (settings_id.equals("0")) {
                    state = new Green(hashElems);
                }

                else if (settings_id.equals("1")) {
                    state = new Black(hashElems);
                }
                else if (settings_id.equals("2")) {
                    state = new Blue(hashElems);
                }

                state.applyChange(context,null);
                Toast.makeText(getApplicationContext(), "Update successfully", Toast.LENGTH_SHORT).show();
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