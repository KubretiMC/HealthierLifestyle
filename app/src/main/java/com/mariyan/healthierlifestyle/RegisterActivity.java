package com.mariyan.healthierlifestyle;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private TextView userName;
    private Spinner userAge;
    private Spinner userGender;
    private Spinner userHeight;
    private Spinner userWeight;
    private Spinner userTrainingsPerWeek;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName = findViewById(R.id.namePlainText);

        register = findViewById(R.id.RegisterButton);
        register.setOnClickListener(v -> {
                if (createUser()) {
                    String username = String.valueOf(userName);
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.putExtra("username", username);
                    StartActivity.startActivity.finish();
                    startActivity(intent);
                    finish();
                }
        });

        userAge = findViewById(R.id.ageSpinner);
        userAge = setSpinner(55,16,userAge);
        userAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        userHeight = findViewById(R.id.heightSpinner);
        userHeight = setSpinner(81,140,userHeight);
        userHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        userWeight = findViewById(R.id.weightSpinner);
        userWeight = setSpinner(201,40,userWeight);
        userWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        userGender = setSpinnerGender("Male", "Female");
        userGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        userTrainingsPerWeek = setSpinnerTrainings("0", "1-3", "3-7");
        userTrainingsPerWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public Spinner setSpinnerGender(String a, String b) {
        String[] arraySpinner = new String[]{a, b};
        Spinner spinner = findViewById(R.id.genderSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return spinner;
    }

    public Spinner setSpinnerTrainings(String a, String b, String c) {
        String[] arraySpinner = new String[]{a, b, c};
        Spinner spinner = findViewById(R.id.trainingsPerWeekSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return spinner;
    }

    public Spinner setSpinner(int spinnerLength,int spinnerStart, Spinner spinner) {
        String[] arraySpinner = new String[spinnerLength];
        for (int i = 0; i < spinnerLength; i++) {
            arraySpinner[i] = String.valueOf(spinnerStart);
            spinnerStart++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return spinner;
    }

    private boolean createUser() {
        String name = userName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter username!", Toast.LENGTH_LONG).show();
            Notification notify = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Empty field!")
                    .setContentText(name)
                    .build();
            notify.flags |= Notification.FLAG_AUTO_CANCEL;
            return false;
        } else if (name.length() < 6 || name.length() > 16) {
            Toast.makeText(getApplicationContext(), "Username must be between 6 and 16 characters", Toast.LENGTH_LONG).show();
            Notification notify = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Username must be between 6 and 16 characters")
                    .setContentText(name)
                    .build();
            notify.flags |= Notification.FLAG_AUTO_CANCEL;
            return false;
        } else {
            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
            Notification notify = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Registration successful!")
                    .setContentText(name)
                    .build();
            notify.flags |= Notification.FLAG_AUTO_CANCEL;
            String age = userAge.getSelectedItem().toString();
            String gender = userGender.getSelectedItem().toString();
            String height = userHeight.getSelectedItem().toString();
            String weight = userWeight.getSelectedItem().toString();
            String trainings = userTrainingsPerWeek.getSelectedItem().toString();

            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("remember", "true");
            editor.apply();

            User.init(getApplicationContext());
            User.setName(name);
            User.setAge(age);
            User.setGender(gender);
            User.setHeight(height);
            User.setWeight(weight);
            User.setTrainings(trainings);
            User.write("name", User.getName());
            User.write("age", User.getAge());
            User.write("gender", User.getGender());
            User.write("height", User.getHeight());
            User.write("weight", User.getWeight());
            User.write("trainings", User.getTrainings());
            return true;
        }
    }
}