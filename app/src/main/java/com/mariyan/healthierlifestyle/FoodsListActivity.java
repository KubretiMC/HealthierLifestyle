package com.mariyan.healthierlifestyle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
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
import com.google.gson.Gson;

public class FoodsListActivity extends AppCompatActivity {
    private String foodType;
    private String name;
    private String proteinsAmount;
    private String carbohydratesAmount;
    private String fatsAmount;
    private String caloriesAmount;
    private int pos = -1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods_list);

        LinearLayout foodsListLayout = findViewById(R.id.foodsListLayout);
        LinearLayout addLayout = findViewById(R.id.addLayout);
        if (!User.getName().equals("")) {
            addLayout.setVisibility(View.VISIBLE);
        } else {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            foodsListLayout.setLayoutParams(param);
        }

        Button calculator = findViewById(R.id.CalculatorButton);
        calculator.setOnClickListener(v -> openCalculatorActivity());

        Button addWanted = findViewById(R.id.WantedButton);
        addWanted.setOnClickListener(v -> addFood(MainActivity.wantedList, "wantedList"));

        Button addUnwanted = findViewById(R.id.UnwantedButton);
        addUnwanted.setOnClickListener(v -> addFood(MainActivity.unwantedList, "unwantedList"));

        foodType = String.valueOf(getIntent().getStringExtra("type"));
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        if (foodType.equals("")) {
            getType(arrayList, "proteins", 0);
        } else if (foodType.equals("calories")) {
            getType(arrayList, foodType, 100);
        } else {
            getType(arrayList, foodType, 10);
        }

        ListView listView = findViewById(R.id.listView);
        ListAdapter adapter = new SimpleAdapter(this, arrayList, R.layout.list_viewdegn,
                new String[]{"name", "protein", "proteins", "carbohydrate", "carbohydrates", "fat", "fats", "calorie", "calories"},
                new int[]{R.id.name, R.id.proteinName, R.id.proteins, R.id.carbohydrateName, R.id.carbohydrates,
                        R.id.fatName, R.id.fats, R.id.calorieName, R.id.calories});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            pos = position;
            name = arrayList.get(position).get("name");
            proteinsAmount = arrayList.get(position).get("proteins");
            carbohydratesAmount = arrayList.get(position).get("carbohydrates");
            fatsAmount = arrayList.get(position).get("fats");
            caloriesAmount = arrayList.get(position).get("calories");
        });
    }

    private String LoadJsonFromAsset() {
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

    private void getType(ArrayList<HashMap<String, String>> arrayList, String type, int biggerThan) {
        try {
            JSONObject obj = new JSONObject(LoadJsonFromAsset());
            JSONArray array = obj.getJSONArray("foods");
            HashMap<String, String> list;
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
                String get = o.getString(type);
                double getDouble = Double.parseDouble(get);
                if (getDouble >= biggerThan) {
                    String name = o.getString("name");
                    String protein = "Proteins:";
                    String proteins = o.getString("proteins");
                    String carbohydrate = "Carbohydrates:";
                    String carbohydrates = o.getString("carbohydrates");
                    String fat = "Fats:";
                    String fats = o.getString("fats");
                    String calorie = "Calories:";
                    String calories = o.getString("calories");
                    list = new HashMap<>();
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

    private void openCalculatorActivity() {
        Intent intent = new Intent(getApplicationContext(), CalculatorActivity.class);
        intent.putExtra("type", foodType);
        startActivity(intent);
    }

    private void addFood(ArrayList<HashMap<String, String>> listWanted, String wanted) {
        if (pos < 0) {
            Toast.makeText(getApplicationContext(), "Please select food!", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, String> list = new HashMap<>();
            String protein = "Proteins:";
            String carbohydrate = "Carbohydrates:";
            String fat = "Fats:";
            String calorie = "Calories:";
            list.put("name", name);
            list.put("protein", protein);
            list.put("proteins", proteinsAmount);
            list.put("carbohydrate", carbohydrate);
            list.put("carbohydrates", carbohydratesAmount);
            list.put("fat", fat);
            list.put("fats", fatsAmount);
            list.put("calorie", calorie);
            list.put("calories", caloriesAmount);
            boolean flag = false;
            for (HashMap<String, String> m : listWanted) {
                if (m.containsValue(name)) {
                    flag = true;
                    Toast.makeText(getApplicationContext(), "Already in", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            if (!flag) {
                listWanted.add(list);
                SharedPreferences db = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor collection = db.edit();
                Gson gson = new Gson();
                String listWantedString = gson.toJson(listWanted);
                collection.putString(wanted, listWantedString);
                collection.apply();
                pos = -1;
                Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            }
        }
    }
}