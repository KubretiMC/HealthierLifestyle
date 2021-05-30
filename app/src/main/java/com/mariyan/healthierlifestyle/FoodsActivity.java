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
                openFoodsListActivity("select * from foods");
            }
        });

        showProtein = findViewById(R.id.ProteinFoodsButton);
        showProtein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFoodsListActivity("select * from foods where proteins>10");
            }
        });

        showCarbo = findViewById(R.id.CarbohydrateFoodsButton);
        showCarbo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFoodsListActivity("select * from foods where carbohydrates>10");
            }
        });

        showFat = findViewById(R.id.FatFoodsButton);
        showFat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFoodsListActivity("select * from foods where fats>10");
            }
        });

        showCalorie = findViewById(R.id.CalorieFoodsButton);
        showCalorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFoodsListActivity("select * from foods where calories>100");
            }
        });


    }
    private void openFoodsListActivity(String query) {
        Intent intent = new Intent(getApplicationContext(), FoodsListActivity2.class);
        intent.putExtra("query", query);
        startActivity(intent);
    }
}
