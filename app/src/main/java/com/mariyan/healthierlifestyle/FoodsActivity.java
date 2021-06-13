package com.mariyan.healthierlifestyle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class FoodsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);

        Button showAll = findViewById(R.id.AllFoodsButton);
        showAll.setOnClickListener(v -> openFoodsListActivity(""));

        Button showProtein = findViewById(R.id.ProteinFoodsButton);
        showProtein.setOnClickListener(v -> openFoodsListActivity("proteins"));

        Button showCarbohydrate = findViewById(R.id.CarbohydrateFoodsButton);
        showCarbohydrate.setOnClickListener(v -> openFoodsListActivity("carbohydrates"));

        Button showFat = findViewById(R.id.FatFoodsButton);
        showFat.setOnClickListener(v -> openFoodsListActivity("fats"));

        Button showCalorie = findViewById(R.id.CalorieFoodsButton);
        showCalorie.setOnClickListener(v -> openFoodsListActivity("calories"));
    }
    private void openFoodsListActivity(String type) {
        Intent intent = new Intent(getApplicationContext(), FoodsListActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
