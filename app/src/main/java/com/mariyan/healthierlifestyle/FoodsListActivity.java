package com.mariyan.healthierlifestyle;

import android.content.Context;
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
    private List<String> sumList;
    private CustomAdapter adapter;
    private Button addWanted;
    private Button addUnwanted;
    private Button sum;
    private Button subtract;
    private Button reset;
    private EditText proteins;
    private EditText carbohydrates;
    private EditText fats;
    private EditText calories;
    private double proteinsAmount = 0;
    private double carbohydratesAmount = 0;
    private double fatsAmount = 0;
    private double caloriesAmount = 0;

    private int pos;

    private ArrayList<ArrayList<String>> list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods_list);

        ListView listView = findViewById(R.id.listview);
        foodType = String.valueOf(getIntent().getStringExtra("type"));

        LinearLayout foodsListLayout = findViewById(R.id.foodsListLayout);
        LinearLayout calculatorLayout = findViewById(R.id.calculatorLayout);
        LinearLayout addLayout = findViewById(R.id.addLayout);
        if (User.getName() != "") {
            calculatorLayout.setVisibility(View.VISIBLE);
            addLayout.setVisibility(View.VISIBLE);
        }else{
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            foodsListLayout.setLayoutParams(param);
        }
        addWanted = findViewById(R.id.WantedButton);
        addUnwanted = findViewById(R.id.UnwantedButton);
        sum = findViewById(R.id.SumButton);
        reset = findViewById(R.id.ResetButton);
        subtract = findViewById(R.id.SubtractButton);
        proteins = findViewById(R.id.proteinsPlainText);
        carbohydrates = findViewById(R.id.proteinsPlainText);
        fats = findViewById(R.id.fatsPlainText);
        calories = findViewById(R.id.caloriesPlainText);



        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        addWanted.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HashMap<String, String> list = new HashMap<>();
                String protein = "Proteins:";
                String carbohydrate = "Carbohydrates:";
                String fat = "Fats:";
                String calorie = "Calories:";

                String name = arrayList.get(pos).get("name");
                String proteins = arrayList.get(pos).get("proteins");
                String carbohydrates = arrayList.get(pos).get("carbohydrates");
                String fats = arrayList.get(pos).get("fats");
                String calories = arrayList.get(pos).get("calories");
                list.put("name", name);
                list.put("protein", protein);
                list.put("proteins", proteins);
                list.put("carbohydrate", carbohydrate);
                list.put("carbohydrates", carbohydrates);
                list.put("fat", fat);
                list.put("fats", fats);
                list.put("calorie", calorie);
                list.put("calories", calories);

                MainActivity.wantedList.add(list);

                SharedPreferences db = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                SharedPreferences.Editor collection = db.edit();
                Gson gson = new Gson();
                String arrayList1 = gson.toJson(MainActivity.wantedList);

                collection.putString("wantedList", arrayList1);
                collection.commit();
            }
        });

        //sum.setOnClickListener(v -> sumNutriens());

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
                proteinsAmount = Double.valueOf(arrayList.get(position).get("proteins"));
                carbohydratesAmount = Double.valueOf(arrayList.get(position).get("carbohydrates"));
                fatsAmount = Double.valueOf(arrayList.get(position).get("fats"));
                caloriesAmount = Double.valueOf(arrayList.get(position).get("calories"));
                pos = position;
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
//            ListAdapter adapter = new SimpleAdapter(this, arrayList, R.layout.list_viewdegn,
//                    new String[]{"name", "protein", "proteins", "carbohydrate", "carbohydrates", "fat", "fats", "calorie", "calories"},
//                    new int[]{R.id.name, R.id.proteinName, R.id.proteins, R.id.carbohydrateName, R.id.carbohydrates,
//                            R.id.fatName, R.id.fats, R.id.calorieName, R.id.calories});
//            listview.setAdapter(adapter);


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
//            ListAdapter adapter = new SimpleAdapter(this, arrayList, R.layout.list_viewdegn,
//                    new String[]{"name", "protein", "proteins", "carbohydrate", "carbohydrates", "fat", "fats", "calorie", "calories"},
//                    new int[]{R.id.name, R.id.proteinName, R.id.proteins, R.id.carbohydrateName, R.id.carbohydrates,
//                            R.id.fatName, R.id.fats, R.id.calorieName, R.id.calories});
//            listview.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    private void sumNutriens(){
//       adapter.getItemId()
//
//    }

}