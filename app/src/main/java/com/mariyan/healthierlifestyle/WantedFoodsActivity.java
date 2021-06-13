package com.mariyan.healthierlifestyle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;

public class WantedFoodsActivity extends AppCompatActivity {
    private String foodType;
    private Button removeWanted;
    private ListView listView;
    private int pos = -1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wanted);

        removeWanted = findViewById(R.id.RemoveButton);
        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener((parent, view, position, id) -> pos = position);

        foodType = String.valueOf(getIntent().getStringExtra("type"));
        if (foodType.equals("wanted")) {
            showFoods(MainActivity.wantedList);
        } else {
            showFoods(MainActivity.unwantedList);
        }

    }

    private void showFoods(ArrayList<HashMap<String, String>> list) {
        ListAdapter adapter = new SimpleAdapter(this, list, R.layout.list_viewdegn,
                new String[]{"name", "protein", "proteins", "carbohydrate", "carbohydrates", "fat", "fats", "calorie", "calories"},
                new int[]{R.id.name, R.id.proteinName, R.id.proteins, R.id.carbohydrateName, R.id.carbohydrates,
                        R.id.fatName, R.id.fats, R.id.calorieName, R.id.calories});
        listView.setAdapter(adapter);

        if (list.isEmpty()) {
            removeWanted.setVisibility(View.GONE);
        } else {
            removeWanted.setOnClickListener(v -> {
                if (pos < 0) {
                    Toast.makeText(getApplicationContext(), "Please select food!", Toast.LENGTH_SHORT).show();
                } else {
                    list.remove(pos);
                    SharedPreferences db = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor collection = db.edit();
                    Gson gson = new Gson();
                    String listWantedString = gson.toJson(list);
                    collection.putString("wantedList", listWantedString);
                    collection.commit();
                    pos = -1;
                    finish();
                    startActivity(getIntent());
                }
            });
        }
    }
}