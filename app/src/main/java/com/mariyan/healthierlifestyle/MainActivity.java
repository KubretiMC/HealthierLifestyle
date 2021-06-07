package com.mariyan.healthierlifestyle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Button foods;
    private String username;
    private MenuInflater inflater;
    public static ArrayList<HashMap<String, String>> wantedList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wantedList = new ArrayList<HashMap<String, String>>();

        username = getIntent().getStringExtra("USERNAME");
        if (username != null && !username.equals("")) {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("username", username);
            editor.apply();

        } else {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            username = prefs.getString("username", "No name defined");//"No name defined" is the default value.

            User.init(getApplicationContext());
            User.setName(User.read("name", ""));
            User.setAge(User.read("age", ""));
            User.setGender(User.read("gender", ""));
            User.setWeight(User.read("weight", ""));
            User.setHeight(User.read("height", ""));
            User.setTrainings(User.read("trainings", ""));




        }
        foods = findViewById(R.id.FoodsButton);
        foods.setOnClickListener(v -> openFoodsActivity());
    }

    public void onDestroy() {
        super.onDestroy();

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        assert checkbox != null;
        if (checkbox.equals("false")) {
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
}