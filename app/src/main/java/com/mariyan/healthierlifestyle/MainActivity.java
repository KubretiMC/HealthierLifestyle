package com.mariyan.healthierlifestyle;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button foods;
    private String username;
    private MenuInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = getIntent().getStringExtra("USERNAME");

        if (username != null && !username.equals("")) {
            //save
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            SharedPreferences.Editor editor = settings.edit();
            editor.putString("username", username);
            editor.apply();
        }else{
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
             username = prefs.getString("username", "No name defined");//"No name defined" is the default value.
        }

        foods = findViewById(R.id.FoodsButton);
        foods.setOnClickListener(v -> openFoodsActivity());
    }

    public void onDestroy() {
        super.onDestroy();

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox=preferences.getString("remember","");
        assert checkbox != null;
        if(checkbox.equals("false")) {

            SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor2 = preferences2.edit();

            editor2.putString("username", "").apply();
            editor2.apply();
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String check = settings.getString("username", null);

        if (check != null && !check.equals("")) {
            inflater = getMenuInflater();
            inflater.inflate(R.menu.profile_menu, menu);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profileItem:
                openProfileActivity();
                return true;
            case R.id.logoutItem:
                openStartActivity();
                finish();
                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();

                SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor2 = preferences2.edit();

                editor2.putString("username","").apply();
                editor2.apply();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openFoodsActivity() {
        Intent intent = new Intent(getApplicationContext(), FoodsActivity.class);
        startActivity(intent);
    }

    private void openProfileActivity() {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }

    private void openStartActivity() {
        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(intent);
    }
}