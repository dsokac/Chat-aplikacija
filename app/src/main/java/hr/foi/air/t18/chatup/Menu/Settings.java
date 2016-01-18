package hr.foi.air.t18.chatup.Menu;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import hr.foi.air.t18.core.State.Context;
import hr.foi.air.t18.chatup.R;
import hr.foi.air.t18.chatup.States.Black;
import hr.foi.air.t18.chatup.States.Green;
import hr.foi.air.t18.chatup.States.Blue;
import hr.foi.air.t18.core.State.State;


/**
 * Public class Settings implements logic of Settings activity
 */
public class Settings extends AppCompatActivity {
    private String settings_id;
    public static Toolbar toolbar_settings;
    public static RelativeLayout relative_layout_stgs;
    public static boolean created=false;
    public static Button btnSettingsSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        created=true;
        toolbar_settings = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_settings);
        relative_layout_stgs = (RelativeLayout) findViewById(R.id.main_layout);

        btnSettingsSave = (Button) findViewById(R.id.btnSettingsSave);
        final RadioGroup settings_radiogroup = (RadioGroup) findViewById(R.id.SettingsColor);
        final Context context = new Context();


        btnSettingsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioButtonID = settings_radiogroup.getCheckedRadioButtonId();
                View radioButton = settings_radiogroup.findViewById(radioButtonID);
                int idx = settings_radiogroup.indexOfChild(radioButton);
                settings_id = Integer.toString(idx);

                State state = null;

                if (settings_id.equals("0")) {
                    state = new Green();
                } else if (settings_id.equals("1")) {
                    state = new Black();
                } else if (settings_id.equals("2")) {
                    state = new Blue();
                }

                state.doAction(context);
                Toast.makeText(getApplicationContext(), "Update successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
