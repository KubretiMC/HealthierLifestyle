package com.mariyan.healthierlifestyle;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
    public static Activity startActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        startActivity = this;

        Button register = findViewById(R.id.RegisterButton);
        register.setOnClickListener(v -> openRegisterActivity());

        Button guest = findViewById(R.id.ContinueAsGuestButton);
        guest.setOnClickListener(v -> openMainActivity());

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkBox = preferences.getString("remember","");
        assert checkBox != null;
        if(checkBox.equals("true")){
            openMainActivity();
            finish();
        }
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
