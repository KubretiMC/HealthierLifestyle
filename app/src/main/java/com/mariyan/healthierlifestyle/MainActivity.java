package com.mariyan.healthierlifestyle;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TakeHeroesFromSQL();



        //List<Foood> fooods = fooodDao.getAll();
        foods = findViewById(R.id.FoodsButton);
        foods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFoodsActivity();
            }
        });

    }


    public void TakeHeroesFromSQL() {




//       //db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath() + "/" + "geroiOpit1.db", null);
//        q = "SELECT * FROM FOODS";
//        Cursor c = db.rawQuery(q, null);
//        while (c.moveToNext()) {
//            Integer id = c.getInt(c.getColumnIndex("ID"));
//            String name = c.getString(c.getColumnIndex("name"));
//            Integer proteins = c.getInt(c.getColumnIndex("proteins"));
//            Integer carbohydrates = c.getInt(c.getColumnIndex("carbohydrates"));
//            Integer fats = c.getInt(c.getColumnIndex("fats"));
//            Integer calories = c.getInt(c.getColumnIndex("calories"));
//            Food food = new Food(id, name,proteins,carbohydrates,fats,calories);
//            Food.list.add(food);
//        }



       // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

       // if (!prefs.getBoolean("firstTime", false)) {




            // <---- run your one time code here
            String q="";

           @SuppressLint("WrongConstant") SQLiteDatabase     db=openOrCreateDatabase("foods.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);
//        createTableIfNotExistsHeroes(q,db);
//        ContentValues values=new ContentValues();
//        values.put("name", "Orange");
//        values.put("proteins",34);
//        values.put("carbohydrates",21);
//        values.put("fats",3);
//        values.put("calories",51);
//        db.insert("foods", null, values);



        q = "SELECT * FROM foods";

            Cursor c = db.rawQuery(q, null);
        c.moveToFirst();
        c.moveToNext();
        while (c.moveToNext()) {
                Integer id = c.getInt(c.getColumnIndex("ID"));
                String name = c.getString(c.getColumnIndex("name"));
                Integer proteins = c.getInt(c.getColumnIndex("proteins"));
                Integer carbohydrates = c.getInt(c.getColumnIndex("carbohydrates"));
                Integer fats = c.getInt(c.getColumnIndex("fats"));
                Integer calories = c.getInt(c.getColumnIndex("calories"));
                Food food = new Food(id,name,proteins,carbohydrates,fats,calories);
              //  Food.list.add(food);
            }
            // marking first time has ran.
//            SharedPreferences.Editor editor = prefs.edit();
  //          editor.putBoolean("firstTime", true);
    //        editor.commit();
            c.close();
            db.close();
       // }

    }

    private void openFoodsActivity() {
        Intent intent = new Intent(getApplicationContext(), FoodsActivity.class);
        startActivity(intent);
    }

    public void createTableIfNotExistsHeroes(String q, SQLiteDatabase db)
    {
        q = "CREATE TABLE if not exists Foods(";
        q += "ID integer primary key AUTOINCREMENT, ";
        q += "name text unique not null, ";
        q += "proteins real not null, ";
        q += "carbohydrates real not null, ";
        q += "fats real not null, ";
        q += "calories real not null);";
        db.execSQL(q);
    }
}
