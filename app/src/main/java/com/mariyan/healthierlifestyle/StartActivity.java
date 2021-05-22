package com.mariyan.healthierlifestyle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
    private Button login;
    private Button register;
    private Button guest;
    public static Activity startActivity1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startActivity1 = this;

        login = findViewById(R.id.LoginButton);
        login.setOnClickListener(v -> openLoginActivity());


        register = findViewById(R.id.RegisterButton);
        register.setOnClickListener(v -> openRegisterActivity());

        guest = findViewById(R.id.ContinueAsGuestButton);
        guest.setOnClickListener(v -> openMainActivity());


        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkBox = preferences.getString("remember","");

        if(checkBox.equals("true")){
            Intent intent = new Intent(StartActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
    private void openLoginActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
    private void openRegisterActivity() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
    private void openMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


}
