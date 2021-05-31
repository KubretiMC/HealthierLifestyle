package com.mariyan.healthierlifestyle;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private TextView userName;
    private Spinner userAge;
    private Spinner userGender;
    private Spinner userHeight;
    private Spinner userWeight;
    private Spinner userTrainingsPerWeek;
    private Button register;
    //private  List<User> userList = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.RegisterButton);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    userName = findViewById(R.id.namePlainText);
                    String username = userName.getText().toString().trim();;
                    if(createUser(username)) {
                        SharedPreferences preferences = getSharedPreferences("save", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("isSaved", "true");
                        editor.apply();


                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                     //   intent.putParcelableArrayListExtra("user", (ArrayList<? extends Parcelable>) userList);
                        StartActivity.startActivity1.finish();
                        startActivity(intent);
                        finish();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        Spinner spinnerAge = setSpinnerAge();
        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner spinnerGender = setSpinner("Male", "Female");
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner spinnerTrainings = setSpinner("0", "1-3", "3-7");
        spinnerTrainings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner spinnerHeight = setSpinnerHeight();
        spinnerHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner spinnerWeight = setSpinnerWeight();
        spinnerWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public Spinner setSpinner(String a, String b) {

        String[] arraySpinner = new String[]{
                a, b
        };
        Spinner spinner = findViewById(R.id.genderSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return spinner;
    }

    public Spinner setSpinner(String a, String b, String c) {

        String[] arraySpinner = new String[]{
                a, b, c
        };
        Spinner spinner = findViewById(R.id.trainingsPerWeekSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return spinner;
    }

    public Spinner setSpinnerHeight() {

        String[] arraySpinner = new String[91];
        int counter = 130;
        for (int i = 0; i <= 90; i++) {
            arraySpinner[i] = String.valueOf(counter);
            counter++;
        }
        Spinner spinner = findViewById(R.id.heightSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return spinner;
    }

    public Spinner setSpinnerAge() {

        String[] arraySpinner = new String[55];
        int counter = 16;
        for (int i = 0; i <= 54; i++) {
            arraySpinner[i] = String.valueOf(counter);
            counter++;
        }
        Spinner spinner = findViewById(R.id.ageSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return spinner;
    }

    public Spinner setSpinnerWeight() {

        String[] arraySpinner = new String[201];
        int counter = 40;
        for (int i = 0; i <= 200; i++) {
            arraySpinner[i] = String.valueOf(counter);
            counter++;
        }
        Spinner spinner = findViewById(R.id.weightSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return spinner;
    }

    private boolean createUser(String username) throws SQLException {
        userAge = findViewById(R.id.ageSpinner);
        userGender = findViewById(R.id.genderSpinner);
        userHeight = findViewById(R.id.heightSpinner);
        userWeight = findViewById(R.id.weightSpinner);
        userTrainingsPerWeek = findViewById(R.id.trainingsPerWeekSpinner);



        if (username.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Empty field", Toast.LENGTH_LONG).show();
            Notification notify = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Empty field!")
                    .setContentText(username)
                    .build();
            notify.flags |= Notification.FLAG_AUTO_CANCEL;
            return false;
        } else if (username.length() < 6 || username.length() > 16) {
            Toast.makeText(getApplicationContext(), "Username must be between 6 and 16 characters", Toast.LENGTH_LONG).show();
            Notification notify = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Username must be between 6 and 16 characters")
                    .setContentText(username)
                    .build();
            notify.flags |= Notification.FLAG_AUTO_CANCEL;
            return false;
        } else {
            try {
                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                Notification notify = new Notification.Builder(getApplicationContext())
                        .setContentTitle("Registration successful!")
                        .setContentText(username)
                        .build();
                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                String age = userAge.getSelectedItem().toString();
                String gender = userGender.getSelectedItem().toString();
                String height = userHeight.getSelectedItem().toString();
                String weight = userWeight.getSelectedItem().toString();
                String trainings = userTrainingsPerWeek.getSelectedItem().toString();

                int heightInt = Integer.parseInt(height);
                int weightInt = Integer.parseInt(weight);
                int ageInt = Integer.parseInt(age);


                User user = new User(username,ageInt,gender,heightInt,weightInt,trainings);
                SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(user);
                prefsEditor.putString("User", json);
                prefsEditor.commit();
                return true;
            } catch (Exception e) {
                Notification notify = new Notification.Builder(getApplicationContext())
                        .setContentTitle("Error while working with database!")
                        .build();
                notify.flags |= Notification.FLAG_AUTO_CANCEL;
            }
        }
        return false;
    }
}
