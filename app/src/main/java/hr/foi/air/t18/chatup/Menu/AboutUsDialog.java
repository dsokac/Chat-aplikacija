package hr.foi.air.t18.chatup.Menu;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import hr.foi.air.t18.chatup.R;

/**
 * Public class AboutUsDialog implements logic of dialog "About us"
 * which is used in MainClass menu
 */
public class AboutUsDialog  extends Dialog  {

    private Context context;
    private Button btnAboutUs;

    public AboutUsDialog(Context context) {
        super(context);
        this.context = context;
        this.setTitle(R.string.about_us_dialog_title);
    }
    //Find reference to btnAboutUsClose and subscribe to onClick event.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_dialog);
        btnAboutUs = (Button) findViewById(R.id.btnAboutUsClose);
        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
