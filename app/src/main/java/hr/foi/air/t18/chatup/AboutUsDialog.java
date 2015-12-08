package hr.foi.air.t18.chatup;

import android.os.Bundle;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;


public class AboutUsDialog  extends Dialog  {

    private Context context;
    private Button btnAboutUs;

    public AboutUsDialog(Context context) {
        super(context);
        this.context = context;
        this.setTitle(R.string.about_us_dialog_title);
    }

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
