package com.valentishealth.app.restconsumer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
   TextView txtuserwelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle extras = getIntent().getExtras();
        if (extras.containsKey("FirstName")) {
            String username = extras.getString("FirstName"); }

            txtuserwelcome = findViewById(R.id.txtWelcome);
        txtuserwelcome.setText("Welcome ");
    }
}
