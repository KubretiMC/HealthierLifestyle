package com.mariyan.healthierlifestyle;

import android.app.Notification;
import android.content.Intent;
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
    private TextView userAge;
    private Spinner userGender;
    private TextView userHeight;
    private TextView userWeight;
    private TextView userTrainingsPerWeek;
    private Object gender;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.RegisterButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createUser(gender);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        Spinner spinner = setSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                gender = parent.getItemAtPosition(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    public Spinner setSpinner() {
        String[] arraySpinner = new String[]{
                "Male", "Female"
        };
        Spinner spinner = (Spinner) findViewById(R.id.genderSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return spinner;
    }

    private void createUser(Object gender) throws SQLException {
        userName = findViewById(R.id.namePlainText);
        userPassword = findViewById(R.id.passwordPlainText);
        userAge = findViewById(R.id.agePlainText);
        userGender = findViewById(R.id.genderSpinner);
        userHeight = findViewById(R.id.heightPlainText);
        userWeight = findViewById(R.id.weightPlainText);
        userTrainingsPerWeek = findViewById(R.id.trainPerWeekPlainText);

        String name = userName.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        String age = userAge.getText().toString().trim();
        String gend = (String) gender;
        String height = userHeight.getText().toString().trim();
        String weight = userWeight.getText().toString().trim();
        String trainsPerWeek = userTrainingsPerWeek.getText().toString().trim();


        if (name.isEmpty() || password.isEmpty() || age.isEmpty() || height.isEmpty() || weight.isEmpty() || trainsPerWeek.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Empty field", Toast.LENGTH_LONG).show();
            Notification notify = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Empty field!")
                    .setContentText(name)
                    .build();
            notify.flags |= Notification.FLAG_AUTO_CANCEL;
        } else {
            String s = "";
            ConnectionHelper connectionHelper = new ConnectionHelper();
            boolean exists = connectionHelper.userCheck(name, false);

            if (exists == true) {
                Toast.makeText(getApplicationContext(), "Username taken!", Toast.LENGTH_LONG).show();
                Notification notify = new Notification.Builder(getApplicationContext())
                        .setContentTitle("Username taken!")
                        .setContentText(name)
                        .build();
                notify.flags |= Notification.FLAG_AUTO_CANCEL;
            } else {
                try {
                    Toast.makeText(getApplicationContext(), "Username free!", Toast.LENGTH_LONG).show();
                    Notification notify = new Notification.Builder(getApplicationContext())
                            .setContentTitle("Username free!")
                            .setContentText(name)
                            .build();
                    notify.flags |= Notification.FLAG_AUTO_CANCEL;
//                    Integer id = Integer.valueOf(Hero.list.size()) + 1;
//                    Hero hero = new Hero(id, name, 1, 5, 200, 1);
//                    Hero.list.add(hero);
//
//
//                    String q = "";
//                    SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath() + "/" + "geroiOpit1.db", null);
//
//                    //long count = DatabaseUtils.queryNumEntries(db, "geroiOpit1");
//                    //int count2 = Integer.valueOf((int) count);
//                    q = "INSERT INTO HEROES(name,attack,hitPoints,unspentPoints,status) VALUES(?,?,?,?,?);";
//                    db.execSQL(q, new Object[]{name, 1, 5, 200, 1});
//                    db.close();
//
//                    Toast.makeText(getApplicationContext(), "Hero created successful!", Toast.LENGTH_LONG).show();
//                    Notification notify = new Notification.Builder(getApplicationContext())
//                            .setContentTitle("Hero created successful")
//                            .setContentText(name)
//                            .build();
//                    notify.flags |= Notification.FLAG_AUTO_CANCEL;
//                    finish();
                } catch (SQLiteException e) {
                    Notification notify = new Notification.Builder(getApplicationContext())
                            .setContentTitle("Error while working with database!")
                            .build();
                    notify.flags |= Notification.FLAG_AUTO_CANCEL;
                } catch (Exception e) {
                    Notification notify = new Notification.Builder(getApplicationContext())
                            .setContentTitle("Error while working with database!")
                            .build();
                    notify.flags |= Notification.FLAG_AUTO_CANCEL;
                }
            }
        }
    }
}


