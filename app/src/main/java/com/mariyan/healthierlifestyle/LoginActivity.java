package com.mariyan.healthierlifestyle;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {
    EditText userName,userPassword;
    CheckBox remember;
    Button login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.namePlainText);
        userPassword = findViewById(R.id.passwordPlainText);

        Toast.makeText(this,"Please Sign In.",Toast.LENGTH_SHORT).show();

        login=findViewById(R.id.LogButton);
        login.setOnClickListener(v -> {
            try {
                if(loginUser(false)) {
                    String username = String.valueOf(userName);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                    StartActivity.startActivity1.finish();
                    finish();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

         remember=findViewById(R.id.checkBoxButton);
         remember.setOnCheckedChangeListener((compoundButton, b) -> {
             if (compoundButton.isChecked()) {
                 SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                 SharedPreferences.Editor editor = preferences.edit();
                 editor.putString("remember", "true");
                 editor.apply();
                 Toast.makeText(LoginActivity.this, "Checked", Toast.LENGTH_SHORT).show();
             } else if (!compoundButton.isChecked()) {
                 SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                 SharedPreferences.Editor editor = preferences.edit();
                 editor.putString("remember", "false");
                 editor.apply();
                 Toast.makeText(LoginActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
             }
         });
    }

    private boolean loginUser(boolean flag) throws SQLException {
        String name = userName.getText().toString().trim();
        String password = userPassword.getText().toString().trim();


        if (name.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Empty field", Toast.LENGTH_LONG).show();
            Notification notify = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Empty field!")
                    .setContentText(name)
                    .build();
            notify.flags |= Notification.FLAG_AUTO_CANCEL;
        } else {
            String s = "";
            ConnectionHelper connectionHelper = new ConnectionHelper();
            boolean exists = connectionHelper.userCheck(name, password);

            if (!exists) {
                Toast.makeText(getApplicationContext(), "Wrong username or password!", Toast.LENGTH_LONG).show();
                Notification notify = new Notification.Builder(getApplicationContext())
                        .setContentTitle("Wrong username or password!")
                        .setContentText(name)
                        .build();
                notify.flags |= Notification.FLAG_AUTO_CANCEL;
            } else {
                try {
                    Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                    Notification notify = new Notification.Builder(getApplicationContext())
                            .setContentTitle("Login successful!")
                            .setContentText(name)
                            .build();
                    notify.flags |= Notification.FLAG_AUTO_CANCEL;


                    flag=true;
                } catch (Exception e) {
                    Notification notify = new Notification.Builder(getApplicationContext())
                            .setContentTitle("Error while working with database!")
                            .build();
                    notify.flags |= Notification.FLAG_AUTO_CANCEL;
                }
            }
        }
        return flag;
    }
}
