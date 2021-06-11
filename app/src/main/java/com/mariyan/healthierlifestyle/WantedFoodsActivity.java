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
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

public class WantedFoodsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private String foodType;
    private List<RowItem> rowItems;
    private CustomAdapter adapter;
    private Button removeWanted;
    private int pos;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wanted);

        ListView listView = findViewById(R.id.listview);
        foodType = String.valueOf(getIntent().getStringExtra("type"));


        ListAdapter adapter = new SimpleAdapter(this, MainActivity.wantedList, R.layout.list_viewdegn,
                new String[]{"name", "protein", "proteins", "carbohydrate", "carbohydrates", "fat", "fats", "calorie", "calories"},
                new int[]{R.id.name, R.id.proteinName, R.id.proteins, R.id.carbohydrateName, R.id.carbohydrates,
                        R.id.fatName, R.id.fats, R.id.calorieName, R.id.calories});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
            }
        });

        removeWanted = findViewById(R.id.RemoveButton);
        removeWanted.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.wantedList.remove(pos);

                SharedPreferences db = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                SharedPreferences.Editor collection = db.edit();
                Gson gson = new Gson();
                String arrayList1 = gson.toJson(MainActivity.wantedList);

                collection.putString("wantedList", arrayList1);
                collection.commit();

                finish();
                startActivity(getIntent());
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
}