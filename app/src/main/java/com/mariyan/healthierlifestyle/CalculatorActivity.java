package com.mariyan.healthierlifestyle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class CalculatorActivity extends AppCompatActivity {
    private EditText proteins;
    private EditText carbohydrates;
    private EditText fats;
    private EditText calories;
    private double proteinsAmount = 0;
    private double carbohydratesAmount = 0;
    private double fatsAmount = 0;
    private double caloriesAmount = 0;
    private double proteinSum = 0;
    private double carboSum = 0;
    private double fatSum = 0;
    private double caloriesSum = 0;
    private int pos = -1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        ListView listView = findViewById(R.id.listView);
        LinearLayout foodsListLayout = findViewById(R.id.foodsListLayout);
        LinearLayout calculatorLayout = findViewById(R.id.calculatorLayout);
        if (!User.getName().equals("")) {
            calculatorLayout.setVisibility(View.VISIBLE);
        } else {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            foodsListLayout.setLayoutParams(param);
        }
        Button sum = findViewById(R.id.SumButton);
        Button reset = findViewById(R.id.ResetButton);
        proteins = findViewById(R.id.proteinsPlainText);
        carbohydrates = findViewById(R.id.carbohydratesPlainText);
        fats = findViewById(R.id.fatsPlainText);
        calories = findViewById(R.id.caloriesPlainText);

        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        sum.setOnClickListener(v -> {
            if (pos < 0) {
                Toast.makeText(getApplicationContext(), "Please select food!", Toast.LENGTH_SHORT).show();
            } else {
                proteinSum += proteinsAmount;
                carboSum += carbohydratesAmount;
                fatSum += fatsAmount;
                caloriesSum += caloriesAmount;
                proteins.setText(String.format("%.2f", proteinSum));
                carbohydrates.setText(String.format("%.2f", carboSum));
                fats.setText(String.format("%.2f", fatSum));
                calories.setText(String.format("%.2f", caloriesSum));
                Toast.makeText(getApplicationContext(), "Calculated", Toast.LENGTH_SHORT).show();
            }
        });

        reset.setOnClickListener(v -> {
            proteinSum = 0;
            carboSum = 0;
            fatSum = 0;
            caloriesSum = 0;
            proteins.setText("0");
            carbohydrates.setText("0");
            fats.setText("0");
            calories.setText("0");
        });
        String foodType = String.valueOf(getIntent().getStringExtra("type"));
        if (foodType.equals("")) {
            getType(arrayList, "proteins", 0);
        } else if (foodType.equals("calories")) {
            getType(arrayList, foodType, 100);
        } else {
            getType(arrayList, foodType, 10);
        }

        ListAdapter adapter = new SimpleAdapter(this, arrayList, R.layout.list_viewdegn,
                new String[]{"name", "protein", "proteins", "carbohydrate", "carbohydrates", "fat", "fats", "calorie", "calories"},
                new int[]{R.id.name, R.id.proteinName, R.id.proteins, R.id.carbohydrateName, R.id.carbohydrates,
                        R.id.fatName, R.id.fats, R.id.calorieName, R.id.calories});
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            proteinsAmount = Double.parseDouble(arrayList.get(position).get("proteins"));
            carbohydratesAmount = Double.parseDouble(arrayList.get(position).get("carbohydrates"));
            fatsAmount = Double.parseDouble(arrayList.get(position).get("fats"));
            caloriesAmount = Double.parseDouble(arrayList.get(position).get("calories"));
            pos = position;
        });
    }
    
    public String LoadJsonFromAsset() {
        String json;
        try {
            InputStream in = this.getAssets().open("mynewfile.json");
            int size = in.available();
            byte[] bbuffer = new byte[size];
            in.read(bbuffer);
            in.close();
            json = new String(bbuffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    public void getType(ArrayList<HashMap<String, String>> arrayList, String type, int biggerThan) {
        try {
            JSONObject obj = new JSONObject(LoadJsonFromAsset());
            JSONArray array = obj.getJSONArray("foods");
            HashMap<String, String> list;
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
                String get = o.getString(type);
                double getDouble = Double.parseDouble(get);
                if (getDouble > biggerThan) {
                    String protein = "Proteins:";
                    String proteins = o.getString("proteins");
                    String carbohydrate = "Carbohydrates:";
                    String carbohydrates = o.getString("carbohydrates");
                    String fat = "Fats:";
                    String fats = o.getString("fats");
                    String calorie = "Calories:";
                    String calories = o.getString("calories");
                    list = new HashMap<>();
                    String name = o.getString("name");
                    list.put("name", name);
                    list.put("protein", protein);
                    list.put("proteins", proteins);
                    list.put("carbohydrate", carbohydrate);
                    list.put("carbohydrates", carbohydrates);
                    list.put("fat", fat);
                    list.put("fats", fats);
                    list.put("calorie", calorie);
                    list.put("calories", calories);
                    arrayList.add(list);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}