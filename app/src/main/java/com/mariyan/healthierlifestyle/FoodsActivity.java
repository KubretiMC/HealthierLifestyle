package com.mariyan.healthierlifestyle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FoodsActivity extends AppCompatActivity {
    private Button showAll;
    private Button showProtein;
    private Button showCarbo;
    private Button showFat;
    private Button showCalorie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);

        showAll = findViewById(R.id.AllFoodsButton);
        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFoodsListActivity("");
            }
        });

        showProtein = findViewById(R.id.ProteinFoodsButton);
        showProtein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFoodsListActivity("proteins");
            }
        });

        showCarbo = findViewById(R.id.CarbohydrateFoodsButton);
        showCarbo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFoodsListActivity("carbohydrates");
            }
        });

        showFat = findViewById(R.id.FatFoodsButton);
        showFat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFoodsListActivity("fats");
            }
        });

        showCalorie = findViewById(R.id.CalorieFoodsButton);
        showCalorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFoodsListActivity("calories");
            }
        });


    }
    private void openFoodsListActivity(String type) {
        Intent intent = new Intent(getApplicationContext(), FoodsListActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
