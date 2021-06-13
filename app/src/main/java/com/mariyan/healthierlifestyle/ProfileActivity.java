package com.mariyan.healthierlifestyle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private final int age = Integer.parseInt(User.getAge());
    private final int height = Integer.parseInt(User.getHeight());
    private final int weight = Integer.parseInt(User.getWeight());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView userName = findViewById(R.id.WelcomeUserTextView);
        userName.setText("Welcome " + User.getName());

        EditText calories = findViewById(R.id.caloriesPlainText);
        calories.setText(String.valueOf(calculateCalories()));

        EditText proteins = findViewById(R.id.proteinsPlainText);
        proteins.setText(String.valueOf(calculateProteins()));

        EditText carbohydrates = findViewById(R.id.carbohydratesPlainText);
        carbohydrates.setText(String.valueOf(calculateCarbohydrates()));

        EditText fats = findViewById(R.id.fatsPlainText);
        fats.setText(String.valueOf(calculateFats()));

        Button updateUser = findViewById(R.id.UpdateButton);
        updateUser.setOnClickListener(v -> openUpdateUserActivity());

        Button wanted = findViewById(R.id.WantedButton);
        wanted.setOnClickListener(v -> openWantedActivity("wanted"));

        Button unwanted = findViewById(R.id.UnwantedButton);
        unwanted.setOnClickListener(v -> openWantedActivity("unwanted"));

    }

    private double calculateCalories() {
        double result;
        if (User.getGender().equals("Male")) {
            result = 66.5 + 13.8 * weight + 5.0 * height - 6.8 * age;
        } else {
            result = 655.1 + 9.6 * weight + 1.9 * height - 4.7 * age;
        }
        if (User.getTrainings().equals("1-3")) {
            result = result * 1.2;
        } else if (User.getTrainings().equals("3-7")) {
            result = result * 1.5;
        }
        return (double) Math.round(result * 100d) / 100d;
    }

    private double calculateFats() {
        return Math.round((calculateCalories() * 0.3 / 9) * 100d) / 100d;
    }

    private double calculateProteins() {
        double result;
        if (User.getTrainings().equals("1-3")) {
            result = Double.parseDouble(User.getWeight()) * 0.8;
        } else if (User.getTrainings().equals("3-7")) {
            result = Double.parseDouble(User.getWeight()) * 1.1;
        } else {
            result = Double.parseDouble(User.getWeight()) * 1.3;
        }
        return Math.round(result * 100d) / 100d;
    }

    private double calculateCarbohydrates() {
        double cal = calculateCalories();
        double fa = calculateFats() * 9;
        double prot = calculateProteins() * 4;
        double carb = (cal - fa - prot) / 4;
        return Math.round(carb * 100d) / 100d;
    }

    private void openWantedActivity(String type) {
        Intent intent = new Intent(getApplicationContext(), WantedFoodsActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    private void openUpdateUserActivity() {
        int LAUNCH_SECOND_ACTIVITY = 1;
        Intent i = new Intent(getApplicationContext(), UpdateUserActivity.class);
        startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_CANCELED) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                finish();
                startActivity(intent);
            }
        }
    }
}