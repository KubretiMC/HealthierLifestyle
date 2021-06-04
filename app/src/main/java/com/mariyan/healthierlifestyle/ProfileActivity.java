package com.mariyan.healthierlifestyle;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.sql.SQLException;

public class ProfileActivity extends AppCompatActivity {
    private TextView userName;
    private Spinner userAge;
    private Spinner userGender;
    private Spinner userHeight;
    private Spinner userWeight;
    private Spinner userTrainingsPerWeek;
    private Button updateUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userName = findViewById(R.id.WelcomeUserTextView);
        userAge = findViewById(R.id.ageSpinner);
        userHeight = findViewById(R.id.heightSpinner);
        userWeight = findViewById(R.id.weightSpinner);
        userTrainingsPerWeek = findViewById(R.id.trainingsPerWeekSpinner);
        updateUser = findViewById(R.id.UpdateButton);
        updateUser.setOnClickListener(v -> {
            try {
                updateUser();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        userName.setText("Welcome "+User.getName());

        Spinner spinnerAge = setSpinnerAge();
        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter ageAdapter = (ArrayAdapter) spinnerAge.getAdapter(); //cast to an ArrayAdapter
        int spinnerAgePosition = ageAdapter.getPosition(User.getAge());
        spinnerAge.setSelection(spinnerAgePosition);

        Spinner spinnerTrainings = setSpinner("0", "1-3", "3-7");
        spinnerTrainings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter trainingsAdapter = (ArrayAdapter) spinnerTrainings.getAdapter(); //cast to an ArrayAdapter
        int spinnerTrainingsPosition = trainingsAdapter.getPosition(User.getTrainings());
        spinnerTrainings.setSelection(spinnerTrainingsPosition);

        Spinner spinnerHeight = setSpinnerHeight();
        spinnerHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter heightAdapter = (ArrayAdapter) spinnerHeight.getAdapter(); //cast to an ArrayAdapter
        int spinnerHeightPosition = heightAdapter.getPosition(User.getHeight());
        spinnerHeight.setSelection(spinnerHeightPosition);

        Spinner spinnerWeight = setSpinnerWeight();
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

    public void updateUser() throws SQLException {


        try {
            String age = userAge.getSelectedItem().toString();
            String height = userHeight.getSelectedItem().toString();
            String weight = userWeight.getSelectedItem().toString();
            String trainings = userTrainingsPerWeek.getSelectedItem().toString();

            int heightInt = Integer.parseInt(height);
            int weightInt = Integer.parseInt(weight);
            int ageInt = Integer.parseInt(age);

//            ConnectionHelper connectionHelper = new ConnectionHelper();
//            connectionHelper.userUpdate(username,ageInt,heightInt,weightInt,trainings);
            //finish();
        } catch (Exception e) {
            Notification notify = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Error while working with database!")
                    .build();
            notify.flags |= Notification.FLAG_AUTO_CANCEL;
        }

    }

}


