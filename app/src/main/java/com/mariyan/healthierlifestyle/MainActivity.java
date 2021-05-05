package com.mariyan.healthierlifestyle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foods = findViewById(R.id.FoodsButton);
        foods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFoodsActivity();
            }
        });

    }

    private void openFoodsActivity() {
        Intent intent = new Intent(getApplicationContext(), FoodsActivity.class);
        startActivity(intent);
    }
}
