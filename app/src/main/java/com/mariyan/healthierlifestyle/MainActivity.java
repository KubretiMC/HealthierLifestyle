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
    public static ArrayList<HashMap<String, String>> unwantedList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wantedList = new ArrayList<>();
        unwantedList = new ArrayList<>();

        wantedList = fillLists(wantedList,"wantedList");
        unwantedList = fillLists(unwantedList,"unwantedList");

        username = getIntent().getStringExtra("username");
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (username != null && !username.equals("")) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("username", username);
            editor.apply();
        } else {
            username = settings.getString("username", "No name defined");//"No name defined" is the default value.
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
        if (item.getItemId() == R.id.profileItem) {
            openProfileActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openFoodsActivity() {
        Intent intent = new Intent(getApplicationContext(), FoodsActivity.class);
        startActivity(intent);
    }

    private void openProfileActivity() {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
    }
    private ArrayList<HashMap<String, String>> fillLists(ArrayList<HashMap<String, String>> list, String wantedList){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String wanted = preferences.getString(wantedList, null);
        if(wanted != null) {
            Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {}.getType();
            list = gson.fromJson(wanted, type);
        }
        return list;
    }
}