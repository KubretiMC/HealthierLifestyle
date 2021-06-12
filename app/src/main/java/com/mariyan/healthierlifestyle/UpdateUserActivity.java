package com.mariyan.healthierlifestyle;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateUserActivity extends AppCompatActivity {
    private Spinner userAge;
    private Spinner userHeight;
    private Spinner userWeight;
    private Spinner userTrainingsPerWeek;
    private EditText calories;
    private EditText proteins;
    private EditText carbohydrates;
    private EditText fats;
    private Button updateUser;
    private int age = Integer.parseInt(User.getAge());
    private int height = Integer.parseInt(User.getHeight());
    private int weight = Integer.parseInt(User.getWeight());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);


        userAge = findViewById(R.id.ageSpinner);
        userHeight = findViewById(R.id.heightSpinner);
        userWeight = findViewById(R.id.weightSpinner);
        userTrainingsPerWeek = findViewById(R.id.trainingsPerWeekSpinner);

        updateUser = findViewById(R.id.UpdateButton);
        updateUser.setOnClickListener(v -> {
            updateUser();
        });

        Spinner spinnerAge = findViewById(R.id.ageSpinner);
        spinnerAge = setSpinner(55, 16, spinnerAge);
        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter ageAdapter = (ArrayAdapter) spinnerAge.getAdapter(); //cast to an ArrayAdapter
        int spinnerAgePosition = ageAdapter.getPosition(User.getAge());
        spinnerAge.setSelection(spinnerAgePosition);

        Spinner spinnerTrainings = setSpinnerTrainings("0", "1-3", "3-7");
        spinnerTrainings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter trainingsAdapter = (ArrayAdapter) spinnerTrainings.getAdapter(); //cast to an ArrayAdapter
        int spinnerTrainingsPosition = trainingsAdapter.getPosition(User.getTrainings());
        spinnerTrainings.setSelection(spinnerTrainingsPosition);

        Spinner spinnerHeight = findViewById(R.id.heightSpinner);
        spinnerHeight = setSpinner(81, 140, spinnerHeight);
        spinnerHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter heightAdapter = (ArrayAdapter) spinnerHeight.getAdapter(); //cast to an ArrayAdapter
        int spinnerHeightPosition = heightAdapter.getPosition(User.getHeight());
        spinnerHeight.setSelection(spinnerHeightPosition);

        Spinner spinnerWeight = findViewById(R.id.weightSpinner);
        spinnerWeight = setSpinner(201, 40, spinnerWeight);
        spinnerWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter weightAdapter = (ArrayAdapter) spinnerWeight.getAdapter(); //cast to an ArrayAdapter
        int spinnerWeightPosition = weightAdapter.getPosition(User.getWeight());
        spinnerWeight.setSelection(spinnerWeightPosition);
    }

    public void updateUser() {
        String age = userAge.getSelectedItem().toString();
        String height = userHeight.getSelectedItem().toString();
        String weight = userWeight.getSelectedItem().toString();
        String trainings = userTrainingsPerWeek.getSelectedItem().toString();

        User.setAge(age);
        User.setHeight(height);
        User.setWeight(weight);
        User.setTrainings(trainings);
        User.write("age", User.getAge());
        User.write("gender", User.getGender());
        User.write("height", User.getHeight());
        User.write("weight", User.getWeight());
        User.write("trainings", User.getTrainings());

        Toast.makeText(getApplicationContext(), "Information updated!", Toast.LENGTH_LONG).show();
        Notification notify = new Notification.Builder(getApplicationContext())
                .setContentTitle("Information updated!")
                .setContentText("Information updated!")
                .build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        Intent intent = getIntent();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
//        finish();
//        startActivity(intent);
    }

    public Spinner setSpinner(int spinnerLength, int spinnerStart, Spinner spinner) {
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

    public Spinner setSpinnerTrainings(String a, String b, String c) {

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
}