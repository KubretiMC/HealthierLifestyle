package com.mariyan.healthierlifestyle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.gson.Gson;

public class FoodsListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private String foodType;
    private List<RowItem> rowItems;
    private CustomAdapter adapter;
    private Button addWanted;
    private Button addUnwanted;
    private Button calculator;
    private EditText proteins;
    private EditText carbohydrates;
    private EditText fats;
    private EditText calories;
    private String name;
    private String proteinsAmount;
    private String carbohydratesAmount;
    private String fatsAmount;
    private String caloriesAmount;

    private int pos = -1;
    private ArrayList<ArrayList<String>> list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods_list);

        ListView listView = findViewById(R.id.listView);
        foodType = String.valueOf(getIntent().getStringExtra("type"));

        LinearLayout foodsListLayout = findViewById(R.id.foodsListLayout);
        LinearLayout addLayout = findViewById(R.id.addLayout);
        if (User.getName() != "") {
            addLayout.setVisibility(View.VISIBLE);
        } else {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            foodsListLayout.setLayoutParams(param);
        }
        calculator = findViewById(R.id.CalculatorButton);
        addWanted = findViewById(R.id.WantedButton);
        addUnwanted = findViewById(R.id.UnwantedButton);
        proteins = findViewById(R.id.proteinsPlainText);
        carbohydrates = findViewById(R.id.carbohydratesPlainText);
        fats = findViewById(R.id.fatsPlainText);
        calories = findViewById(R.id.caloriesPlainText);

        calculator.setOnClickListener(v -> openCalculatorActivity());

        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        addWanted.setOnClickListener(v -> {
            addFood(MainActivity.wantedList);
        });

        addUnwanted.setOnClickListener(v -> {
            addFood(MainActivity.unwantedList);
        });

        if (foodType.equals("")) {
            getAll(arrayList);
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                name = arrayList.get(pos).get("name");
                proteinsAmount = arrayList.get(position).get("proteins");
                carbohydratesAmount = arrayList.get(position).get("carbohydrates");
                fatsAmount = arrayList.get(position).get("fats");
                caloriesAmount = arrayList.get(position).get("calories");
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String food_name = rowItems.get(position).getFood_name();
        Toast.makeText(getApplicationContext(), "" + food_name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("Main", " data search" + newText);
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search_view) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public String LoadJsonFromAsset() {
        String json;
        try {
            InputStream in = this.getAssets().open("mynewfile.json");
            int size = in.available();
            byte[] bbuffer = new byte[size];
            in.read(bbuffer);
            in.close();
            json = new String(bbuffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    public void getAll(ArrayList<HashMap<String, String>> arrayList) {
        try {
            JSONObject obj = new JSONObject(LoadJsonFromAsset());
            JSONArray array = obj.getJSONArray("foods");
            HashMap<String, String> list;
//            ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getType(ArrayList<HashMap<String, String>> arrayList, String type, int biggerThan) {
        try {
            JSONObject obj = new JSONObject(LoadJsonFromAsset());
            JSONArray array = obj.getJSONArray("foods");
            HashMap<String, String> list;
            // ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
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
    private void openCalculatorActivity() {
        Intent intent = new Intent(getApplicationContext(), CalculatorActivity.class);
        intent.putExtra("type", foodType);
        startActivity(intent);
    }
    public void addFood(ArrayList<HashMap<String, String>> listWanted){
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
                    String arrayList1 = gson.toJson(listWanted);
                    collection.putString("wantedList", arrayList1);
                    collection.commit();
                    pos = -1;
                    Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                }
            }
        };
}