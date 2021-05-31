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
import com.google.gson.Gson;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button foods;
    private String username;
    private MenuInflater inflater;
    private List<User> userList = new ArrayList<User>();
    private User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("User", "");
        user = gson.fromJson(json, User.class);

//        userList = getIntent().getParcelableArrayListExtra("user");
//
//        if (!userList.isEmpty()) {
//            User user = new User();
//            SharedPreferences appSharedPrefs = PreferenceManager
//                    .getDefaultSharedPreferences(this.getApplicationContext());
//            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
//            Gson gson = new Gson();
//            String json = gson.toJson(user);
//            prefsEditor.putString("MyUser", json);
//
//            prefsEditor.commit();
//
//
//        }else{
//            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//            username = prefs.getString("username", "No name defined");//"No name defined" is the default value.
//        }

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
        SharedPreferences preferences = getSharedPreferences("save", MODE_PRIVATE);
        String checkBox = preferences.getString("isSaved","");

        if(checkBox.equals("true")){
            inflater = getMenuInflater();
            inflater.inflate(R.menu.profile_menu, menu);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profileItem:
                openProfileActivity(user);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openFoodsActivity() {
        Intent intent = new Intent(getApplicationContext(), FoodsActivity.class);
        startActivity(intent);
    }

    private void openProfileActivity(User user) {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        //   intent.putParcelableArrayListExtra("user", (ArrayList<? extends Parcelable>) userList);
        intent.putExtra("userStats", user);

        startActivity(intent);
    }

    private void openStartActivity() {
        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(intent);
    }
}