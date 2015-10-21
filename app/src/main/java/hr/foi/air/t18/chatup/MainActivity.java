package hr.foi.air.t18.chatup;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find reference to btn_register and subscribe to onClick event.
        Button btnRegister = (Button)findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(i);
            }
        });
    }
}