package com.mariyan.healthierlifestyle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.google.gson.Gson;
import java.util.List;

public class WantedFoodsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private String wanted;
    private String foodType;
    private List<RowItem> rowItems;
    private CustomAdapter adapter;
    private Button removeWanted;
    private Button removeUnwanted;
    private int pos = -1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wanted);

        ListView listView = findViewById(R.id.listView);
        foodType = String.valueOf(getIntent().getStringExtra("type"));

        if (foodType.equals("wanted")) {
            removeWanted = findViewById(R.id.RemoveButton);
            ListAdapter adapter = new SimpleAdapter(this, MainActivity.wantedList, R.layout.list_viewdegn,
                    new String[]{"name", "protein", "proteins", "carbohydrate", "carbohydrates", "fat", "fats", "calorie", "calories"},
                    new int[]{R.id.name, R.id.proteinName, R.id.proteins, R.id.carbohydrateName, R.id.carbohydrates,
                            R.id.fatName, R.id.fats, R.id.calorieName, R.id.calories});
            listView.setAdapter(adapter);

            if (MainActivity.wantedList.isEmpty()) {
                removeWanted.setVisibility(View.GONE);
            }

            removeWanted.setOnClickListener(v -> {
                if (pos < 0) {
                    Toast.makeText(getApplicationContext(), "Please select food!", Toast.LENGTH_SHORT).show();
                } else {
                    MainActivity.wantedList.remove(pos);

                    SharedPreferences db = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                    SharedPreferences.Editor collection = db.edit();
                    Gson gson = new Gson();
                    String arrayList1 = gson.toJson(MainActivity.wantedList);

                    collection.putString("wantedList", arrayList1);
                    collection.commit();
                    pos = -1;
                    finish();
                    startActivity(getIntent());
                }
            });
        } else {

            removeUnwanted = findViewById(R.id.RemoveButton);
            ListAdapter adapter = new SimpleAdapter(this, MainActivity.unwantedList, R.layout.list_viewdegn,
                    new String[]{"name", "protein", "proteins", "carbohydrate", "carbohydrates", "fat", "fats", "calorie", "calories"},
                    new int[]{R.id.name, R.id.proteinName, R.id.proteins, R.id.carbohydrateName, R.id.carbohydrates,
                            R.id.fatName, R.id.fats, R.id.calorieName, R.id.calories});
            listView.setAdapter(adapter);

            if (MainActivity.unwantedList.isEmpty()) {
                removeUnwanted.setVisibility(View.GONE);
            }

            removeUnwanted.setOnClickListener(v -> {
                if (pos < 0) {
                    Toast.makeText(getApplicationContext(), "Please select food!", Toast.LENGTH_SHORT).show();
                } else {
                    MainActivity.unwantedList.remove(pos);
                    SharedPreferences db = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor collection = db.edit();
                    Gson gson = new Gson();
                    String arrayList1 = gson.toJson(MainActivity.unwantedList);
                    collection.putString("unwantedList", arrayList1);
                    collection.commit();
                    pos = -1;
                    finish();
                    startActivity(getIntent());
                }
            });
        }
        listView.setOnItemClickListener((parent, view, position, id) -> pos = position);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String food_name = rowItems.get(position).getFood_name();
        Toast.makeText(getApplicationContext(), "" + food_name, Toast.LENGTH_SHORT).show();
    }

   

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search_view) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}