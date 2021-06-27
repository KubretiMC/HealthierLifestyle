package com.mariyan.healthierlifestyle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateUserActivity extends AppCompatActivity {
    private Spinner userAge;
    private Spinner userHeight;
    private Spinner userWeight;
    private Spinner userTrainingsPerWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        Button updateUser = findViewById(R.id.UpdateButton);
        updateUser.setOnClickListener(v -> updateUser());

        userAge = findViewById(R.id.ageSpinner);
        setSpinner(55, 16, userAge);
        ArrayAdapter ageAdapter = (ArrayAdapter) userAge.getAdapter(); //cast to an ArrayAdapter
        int spinnerAgePosition = ageAdapter.getPosition(User.getAge());
        userAge.setSelection(spinnerAgePosition);

        userTrainingsPerWeek = findViewById(R.id.trainingsPerWeekSpinner);
        userTrainingsPerWeek = setSpinnerTrainings();
        ArrayAdapter trainingsAdapter = (ArrayAdapter) userTrainingsPerWeek.getAdapter(); //cast to an ArrayAdapter
        int spinnerTrainingsPosition = trainingsAdapter.getPosition(User.getTrainings());
        userTrainingsPerWeek.setSelection(spinnerTrainingsPosition);

        userHeight = findViewById(R.id.heightSpinner);
        setSpinner(81, 140, userHeight);
        ArrayAdapter heightAdapter = (ArrayAdapter) userHeight.getAdapter(); //cast to an ArrayAdapter
        int spinnerHeightPosition = heightAdapter.getPosition(User.getHeight());
        userHeight.setSelection(spinnerHeightPosition);

        userWeight = findViewById(R.id.weightSpinner);
        setSpinner(201, 40, userWeight);
        ArrayAdapter weightAdapter = (ArrayAdapter) userWeight.getAdapter(); //cast to an ArrayAdapter
        int spinnerWeightPosition = weightAdapter.getPosition(User.getWeight());
        userWeight.setSelection(spinnerWeightPosition);
    }

    private void updateUser() {
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
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    private void setSpinner(int spinnerLength, int spinnerStart, Spinner spinner) {
        String[] arraySpinner = new String[spinnerLength];
        for (int i = 0; i < spinnerLength; i++) {
            arraySpinner[i] = String.valueOf(spinnerStart);
            spinnerStart++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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
}