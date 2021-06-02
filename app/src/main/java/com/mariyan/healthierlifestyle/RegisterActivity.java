package com.mariyan.healthierlifestyle;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;

public class RegisterActivity extends AppCompatActivity {

    private TextView userName;
    private TextView userPassword;
    private TextView userPassword2;
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

        register = findViewById(R.id.RegisterButton);
        register.setOnClickListener(v -> {
            try {
                if(createUser(false)){
                    String username = String.valueOf(userName);
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.putExtra("USERNAME", username);
                    StartActivity.startActivity1.finish();
                    startActivity(intent);
                    finish();
                }

            } catch (SQLException e) {
                e.printStackTrace();
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

    private boolean createUser(boolean flag) throws SQLException {
        userName = findViewById(R.id.namePlainText);
        userPassword = findViewById(R.id.passwordPlainText);
        userPassword2 = findViewById(R.id.confirmPasswordPlainText);
        userAge = findViewById(R.id.ageSpinner);
        userGender = findViewById(R.id.genderSpinner);
        userHeight = findViewById(R.id.heightSpinner);
        userWeight = findViewById(R.id.weightSpinner);
        userTrainingsPerWeek = findViewById(R.id.trainingsPerWeekSpinner);

        String name = userName.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        String password2 = userPassword2.getText().toString().trim();


        if (name.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Empty field", Toast.LENGTH_LONG).show();
            Notification notify = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Empty field!")
                    .setContentText(name)
                    .build();
            notify.flags |= Notification.FLAG_AUTO_CANCEL;
        } else if (name.length() < 6 || name.length() > 16 || password.length() < 6 || password.length() > 16) {
            Toast.makeText(getApplicationContext(), "Username and password must be between 6 and 16 characters", Toast.LENGTH_LONG).show();
            Notification notify = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Username and password must be between 6 and 16 characters")
                    .setContentText(name)
                    .build();
            notify.flags |= Notification.FLAG_AUTO_CANCEL;
        } else if (!password.equals(password2)) {
            Toast.makeText(getApplicationContext(), "Passwords don't match.", Toast.LENGTH_LONG).show();
            Notification notify = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Passwords don't match.")
                    .setContentText(name)
                    .build();
            notify.flags |= Notification.FLAG_AUTO_CANCEL;
        }else {
            String s = "";
            ConnectionHelper connectionHelper = new ConnectionHelper();
            boolean exists = connectionHelper.userNameCheck(name);

            if (exists) {
                Toast.makeText(getApplicationContext(), "Username taken!", Toast.LENGTH_LONG).show();
                Notification notify = new Notification.Builder(getApplicationContext())
                        .setContentTitle("Username taken!")
                        .setContentText(name)
                        .build();
                notify.flags |= Notification.FLAG_AUTO_CANCEL;
            } else {
                try {
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

                    flag = true;
                    int heightInt = Integer.parseInt(height);
                    int weightInt = Integer.parseInt(weight);
                    int ageInt = Integer.parseInt(age);


                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();

                    User.setName(name);
                    User.setAge(age);
                    User.setGender(gender);
                    User.setHeight(height);
                    User.setWeight(weight);
                    User.setTrainings(trainings);
                    String userr= User.getAge();
                    User.write("name",User.getName());
                    User.write("age",User.getAge());
                    User.write("gender",User.getGender());
                    User.write("height",User.getHeight());
                    User.write("weight",User.getWeight());
                    User.write("trainings",User.getTrainings());

                    connectionHelper.userRegister(name, password, ageInt, gender, heightInt, weightInt, trainings);


                    //finish();
                } catch (Exception e) {
                    Notification notify = new Notification.Builder(getApplicationContext())
                            .setContentTitle("Error while working with database!")
                            .build();
                    notify.flags |= Notification.FLAG_AUTO_CANCEL;
                }
            }
        }
        return flag;
    }
}


