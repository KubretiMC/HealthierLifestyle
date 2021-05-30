package com.mariyan.healthierlifestyle;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoodsListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String foodType;
    List<RowItem> rowItems;
    CustomAdapter adapter;
    ListView listview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods_list);

        listview = findViewById(R.id.listview);
        foodType = String.valueOf(getIntent().getStringExtra("type"));
        if(foodType.equals("")){
           getAll();
        }else if(foodType.equals("calories")){
            getType(foodType,100);
        } else {
            getType(foodType,10);
        }
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
        String json = null;
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

    public void getAll() {
        try {
            JSONObject obj = new JSONObject(LoadJsonFromAsset());
            JSONArray array = obj.getJSONArray("foods");
            HashMap<String, String> list;
            ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
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
            ListAdapter adapter = new SimpleAdapter(this, arrayList, R.layout.list_viewdegn,
                    new String[]{"name", "protein", "proteins", "carbohydrate", "carbohydrates", "fat", "fats", "calorie", "calories"},
                    new int[]{R.id.name, R.id.proteinName, R.id.proteins, R.id.carbohydrateName, R.id.carbohydrates,
                            R.id.fatName, R.id.fats, R.id.calorieName, R.id.calories});
            listview.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getType(String type, int biggerThan) {
        try {
            JSONObject obj = new JSONObject(LoadJsonFromAsset());
            JSONArray array = obj.getJSONArray("foods");
            HashMap<String, String> list;
            ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
                String get = o.getString(type);
                double getDouble=Double.parseDouble(get);
                if(getDouble>biggerThan){
                    String protein = "Proteins:";
                    String proteins = o.getString("proteins");
                    String carbohydrate = "Carbohydrates:";
                    String carbohydrates = o.getString("carbohydrates");
                    String fat = "Fats:";
                    String fats = o.getString("fats");
                    String calorie = "Calories:";
                    String calories=o.getString("calories");
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
            ListAdapter adapter = new SimpleAdapter(this, arrayList, R.layout.list_viewdegn,
                    new String[]{"name", "protein", "proteins", "carbohydrate", "carbohydrates", "fat", "fats", "calorie", "calories"},
                    new int[]{R.id.name, R.id.proteinName, R.id.proteins, R.id.carbohydrateName, R.id.carbohydrates,
                            R.id.fatName, R.id.fats, R.id.calorieName, R.id.calories});
            listview.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}