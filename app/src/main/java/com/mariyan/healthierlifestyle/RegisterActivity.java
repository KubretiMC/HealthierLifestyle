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

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterActivity extends AppCompatActivity {

    private TextView userName;
    private TextView userPassword;
    private TextView userPassword2;
    private TextView userAge;
    private Spinner userGender;
    private Spinner userHeight;
    private Spinner userWeight;
    private Spinner userTrainingsPerWeek;
    private Object gender;
    private Object height;
    private Object weight;
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
                    createUser();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        Spinner spinnerGender = setSpinner("Male","Female");
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                gender = parent.getItemAtPosition(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner spinnerTrainings = setSpinner("0","1-3","3-7");
        spinnerTrainings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                gender = parent.getItemAtPosition(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner spinnerHeight = setSpinnerHeight();
        spinnerHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                height = parent.getItemAtPosition(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner spinnerWeight = setSpinnerWeight();
        spinnerWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                weight = parent.getItemAtPosition(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public Spinner setSpinner(String a, String b) {

        String[] arraySpinner = new String[]{
                a,b
        };
        Spinner spinner = (Spinner) findViewById(R.id.genderSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return spinner;
    }
    public Spinner setSpinner(String a, String b, String c) {

        String[] arraySpinner = new String[]{
                a,b,c
        };
        Spinner spinner = (Spinner) findViewById(R.id.trainingsPerWeekSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return spinner;
    }
    public Spinner setSpinnerHeight() {

        String[] arraySpinner = new String[91];
        int counter=130;
        for(int i=0;i<=90;i++){
            arraySpinner[i]= String.valueOf(counter);
            counter++;
        }
        Spinner spinner = (Spinner) findViewById(R.id.heightSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return spinner;
    }
    public Spinner setSpinnerWeight() {

        String[] arraySpinner = new String[201];
        int counter=40;
        for(int i=0;i<=200;i++){
            arraySpinner[i]= String.valueOf(counter);
            counter++;
        }
        Spinner spinner = (Spinner) findViewById(R.id.weightSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return spinner;
    }

    private void createUser() throws SQLException {
        userName = findViewById(R.id.namePlainText);
        userPassword = findViewById(R.id.passwordPlainText);
        userPassword2 = findViewById(R.id.confirmPasswordPlainText);
        userAge = findViewById(R.id.agePlainText);
        userGender = findViewById(R.id.genderSpinner);
        userHeight = findViewById(R.id.heightSpinner);
        userWeight = findViewById(R.id.weightSpinner);
        userTrainingsPerWeek = findViewById(R.id.trainingsPerWeekSpinner);

        String name = userName.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        String password2 = userPassword2.getText().toString().trim();
        String age = userAge.getText().toString().trim();





        if (name.isEmpty() || password.isEmpty() || age.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Empty field", Toast.LENGTH_LONG).show();
            Notification notify = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Empty field!")
                    .setContentText(name)
                    .build();
            notify.flags |= Notification.FLAG_AUTO_CANCEL;
        }else if(name.length()<6 || name.length()>16 || password.length()<6 || password.length()>16){
            Toast.makeText(getApplicationContext(), "Username and password must be between 6 and 16 characters", Toast.LENGTH_LONG).show();
            Notification notify = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Username and password must be between 6 and 16 characters")
                    .setContentText(name)
                    .build();
            notify.flags |= Notification.FLAG_AUTO_CANCEL;
        }else if(!password.equals(password2)){
            Toast.makeText(getApplicationContext(), "Passwords don't match.", Toast.LENGTH_LONG).show();
            Notification notify = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Passwords don't match.")
                    .setContentText(name)
                    .build();
            notify.flags |= Notification.FLAG_AUTO_CANCEL;
        }else if(Integer.valueOf(age)<16 || Integer.valueOf(age)>70){
            Toast.makeText(getApplicationContext(), "Enter age between 16 and 70.", Toast.LENGTH_LONG).show();
            Notification notify = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Enter age between 16 and 70.")
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
            String gender = userGender.getSelectedItem().toString();
            String height = userHeight.getSelectedItem().toString();
            String weight = userWeight.getSelectedItem().toString();
            String trainings = userTrainingsPerWeek.getSelectedItem().toString();

            int heightInt = Integer.parseInt(height);
            int weightInt = Integer.parseInt(weight);
            int ageInt = Integer.parseInt(age);
              connectionHelper.userRegister(name,password,ageInt,gender,heightInt,weightInt,trainings);

//                    PreparedStatement st = connectionHelper.connectionclass().prepareStatement("INSERT INTO users (name,password,age,gender,height,weight,trainings) VALUES (?,?,?,?,?,?,?)");
//                    st.setString(1, name);
//                    st.setString(2, password);
//                    st.setInt(3, ageInt);
//                    st.setString(4, gender);
//                    st.setInt(5, heightInt);
//                    st.setInt(6, weightInt);
//                    st.setString(7, trainings);
//                    st.executeQuery();




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


