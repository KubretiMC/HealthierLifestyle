package com.mariyan.healthierlifestyle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;


public class FoodsActivity extends AppCompatActivity {
    Button load;
    Connection connect;
    String ConnectionResult = "";

    //@RequiresApi(api = Build.VERSION_CODES.N)
    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);
        GetTextFromSQL();



    }

    public ListView GetTextFromSQL() {
        final ListView simpleList = findViewById(R.id.simpleListView);
        ArrayList<String> listResults = new ArrayList<>();

        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {
                String query = "Select * from foods";
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);


                while (rs.next()) {
                    listResults.add(rs.getString(2)+"   Calories:"+rs.getString(6));
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                        getApplicationContext(),
                        R.layout.activity_list_view,
                        R.id.result,
                        listResults
                );
                simpleList.setAdapter(arrayAdapter);
                return simpleList;
            } else {
                ConnectionResult = "Check Connection";
            }
        } catch (Exception ex) {
            Log.e("error", Objects.requireNonNull(ex.getMessage()));
        }
        return simpleList;
    }
}