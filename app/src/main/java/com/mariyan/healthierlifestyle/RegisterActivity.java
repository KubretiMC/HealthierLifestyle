package com.mariyan.healthierlifestyle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName = findViewById(R.id.namePlainText);

        Button register = findViewById(R.id.RegisterButton);
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
        setSpinner(55, 16, userAge);

        userHeight = findViewById(R.id.heightSpinner);
        setSpinner(81, 140, userHeight);

        userWeight = findViewById(R.id.weightSpinner);
        setSpinner(201, 40, userWeight);

        userGender = setSpinnerGender();
        userTrainingsPerWeek = setSpinnerTrainings();
    }

    private Spinner setSpinnerGender() {
        String[] arraySpinner = new String[]{"Male", "Female"};
        Spinner spinner = findViewById(R.id.genderSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return spinner;
    }

    private Spinner setSpinnerTrainings() {
        String[] arraySpinner = new String[]{"0", "1-3", "3-7"};
        Spinner spinner = findViewById(R.id.trainingsPerWeekSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return spinner;
    }

    private void setSpinner(int spinnerLength, int spinnerStart, Spinner spinner) {
        String[] arraySpinner = new String[spinnerLength];
        for (int i = 0; i < spinnerLength; i++) {
            arraySpinner[i] = String.valueOf(spinnerStart);
            spinnerStart++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private boolean createUser() {
        String name = userName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter username!", Toast.LENGTH_LONG).show();
            return false;
        } else if (name.length() < 6 || name.length() > 16) {
            Toast.makeText(getApplicationContext(), "Username must be between 6 and 16 characters", Toast.LENGTH_LONG).show();
            return false;
        } else {
            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
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