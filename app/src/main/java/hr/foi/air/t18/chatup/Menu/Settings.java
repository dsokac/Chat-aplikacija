package hr.foi.air.t18.chatup.Menu;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import hr.foi.air.t18.core.State.Context;
import hr.foi.air.t18.chatup.R;
import hr.foi.air.t18.chatup.States.Black;
import hr.foi.air.t18.chatup.States.Green;
import hr.foi.air.t18.chatup.States.Blue;


/**
 * Public class Settings implements logic of Settings activity
 */
public class Settings extends AppCompatActivity {
    private String settings_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnSave = (Button) findViewById(R.id.btnSettingsSave);
        final RadioGroup settings_radiogroup = (RadioGroup) findViewById(R.id.SettingsColor);
        final Context context = new Context();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioButtonID = settings_radiogroup.getCheckedRadioButtonId();
                View radioButton = settings_radiogroup.findViewById(radioButtonID);
                int idx = settings_radiogroup.indexOfChild(radioButton);
                settings_id = Integer.toString(idx);

                if (settings_id.equals("0")) {
                    Green green = new Green();
                    green.doAction(context);
                    Toast.makeText(getApplicationContext(), "Update successfully", Toast.LENGTH_SHORT).show();
                } else if (settings_id.equals("1")) {
                    Black black = new Black();
                    black.doAction(context);
                    Toast.makeText(getApplicationContext(), "Update successfully", Toast.LENGTH_SHORT).show();
                } else if (settings_id.equals("2")) {
                    Blue blue = new Blue();
                    blue.doAction(context);
                    Toast.makeText(getApplicationContext(), "Update successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
