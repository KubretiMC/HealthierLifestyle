package com.mariyan.healthierlifestyle;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.CursorAdapter;

import java.util.ArrayList;


public class FoodsActivity extends AppCompatActivity {
    private TextView res;
    private foodsHelper dbFoodsHelper = null;
    //private Cursor ourCursor = null;
   // private FoodAdapter adapter = null;

    //@RequiresApi(api = Build.VERSION_CODES.N)
    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_foods);

            ListView myListView = findViewById(R.id.simpleListView);

            dbFoodsHelper = new foodsHelper(this);
            dbFoodsHelper.createDatabase();

            dbFoodsHelper = new foodsHelper(this);
            dbFoodsHelper.createDatabase();
            dbFoodsHelper.openDataBase();
           // ourCursor = dbFoodsHelper.getCursor();
            //startManagingCursor(ourCursor);
            //adapter = new FoodAdapter(ourCursor);
            //myListView.setAdapter(adapter);

            res = findViewById(R.id.result);
            final ListView simpleList = findViewById(R.id.simpleListView);

            ArrayList<String> listResults = new ArrayList<>();

            for(int i = 0; i<5; i++) {

                listResults.add("dsada");
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                    getApplicationContext(),
                    R.layout.activity_list_view,
                    R.id.result,
                    listResults
            );
            simpleList.setAdapter(arrayAdapter);

            ListView heroesList=simpleList;

        } catch (Exception e) {
            Log.e("ERROR", "ERROR N CODE. " + e.toString());
            e.printStackTrace();
        }
    }

    private ArrayList<Food> loadEmployeesFromDatabase(ArrayList<Food> listResults ){
        String sql = "SELECT * FROM FOODS";

        Cursor cursor =dbFoodsHelper.dbSQLite.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do {
                listResults.add(new Food(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getFloat(2),
                        cursor.getFloat(3),
                        cursor.getFloat(4),
                        cursor.getFloat(5)
                ));
            }while (cursor.moveToNext());
        }
        return listResults;
    }

    class FoodAdapter extends CursorAdapter {
        FoodAdapter(Cursor c){
            super(FoodsActivity.this,c);
        }
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater =getLayoutInflater();
            View row = inflater.inflate(R.layout.activity_foods,parent,false);
            FoodHolder holder = new FoodHolder(row);
            row.setTag(holder);
            return(row);
        }

        @Override
        public void bindView(View row, Context context, Cursor c) {
            FoodHolder holder =(FoodHolder) row.getTag();
            holder.populateForm(c,dbFoodsHelper);
        }
    }

    static class FoodHolder{
        private TextView name = null;

        FoodHolder(View row){
            name = row.findViewById(R.id.simpleListView);
        }
        void populateForm(Cursor c, foodsHelper r){
            name.setText(r.getName(c));
        }
    }
}