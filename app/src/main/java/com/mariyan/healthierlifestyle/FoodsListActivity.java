package com.mariyan.healthierlifestyle;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;


public class FoodsListActivity extends AppCompatActivity {
    Button load;
    Connection connect;
    String ConnectionResult = "";
    String query;
    //@RequiresApi(api = Build.VERSION_CODES.N)
    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods_list);
        query= String.valueOf(getIntent().getStringExtra("query"));
        GetTextFromSQL(query);


    }

    public ListView GetTextFromSQL(String query) {
        final ListView simpleList = findViewById(R.id.simpleListView);
        ArrayList<String> listResults = new ArrayList<>();

        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {
                //String query = "Select * from foods";
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);


                while (rs.next()) {
                    listResults.add(rs.getString(2)+ System.lineSeparator() +"Calories:"+rs.getString(6)
                            + System.lineSeparator() +"Proteins: "+rs.getString(3)
                            + System.lineSeparator() +"Carbohydrates: "+rs.getString(4)
                            + System.lineSeparator() +"Fats: "+rs.getString(5));
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