package com.mariyan.healthierlifestyle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FoodsListActivity extends Activity implements AdapterView.OnItemClickListener {
    Button load;
    Connection connect;
    String ConnectionResult = "";
    String query;

    String[] food_names;
    TypedArray food_pics;
    List<RowItem> rowItems;
    ListView mylistview;

    Context context = this;

    //@RequiresApi(api = Build.VERSION_CODES.N)
    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods_list);

        query= String.valueOf(getIntent().getStringExtra("query"));
        GetTextFromSQL(query);


        loadImageByInternetURL();

//        rowItems = new ArrayList<RowItem>();
//        food_names = getResources().getStringArray(R.array.Foods_names);
//        food_pics=getResources().obtainTypedArray(R.array.foods_pics);
//        for(int i=0;i<food_names.length;i++){
//            RowItem item = new RowItem(food_names[i],
//                    food_pics.getResourceId(i,-1));
//            rowItems.add(item);
//        }
//        mylistview = (ListView) findViewById(R.id.simpleListView);
//        CustomAdapter adapter = new CustomAdapter(this,rowItems);
//        mylistview.setAdapter(adapter);
//
//        mylistview.setOnItemClickListener(this);

    }

    public ListView GetTextFromSQL(String query) {
        final ListView simpleList = findViewById(R.id.simpleListView);
        int images[] = {R.drawable.apple,R.drawable.orange,R.drawable.egg,R.drawable.rice,R.drawable.chickenbreasts};
        ArrayList<String> listResults = new ArrayList<>();

        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {
                //String query = "Select * from foods";
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                rowItems = new ArrayList<RowItem>();

                int counter=0;
                while (rs.next()) {
//                    listResults.add(rs.getString(2)+ System.lineSeparator() +"Calories:"+rs.getString(6)
//                            + System.lineSeparator() +"Proteins: "+rs.getString(3)
//                            + System.lineSeparator() +"Carbohydrates: "+rs.getString(4)
//                            + System.lineSeparator() +"Fats: "+rs.getString(5)
//                            + System.lineSeparator() + images[counter]);
//                    counter++;
                    food_pics=getResources().obtainTypedArray(R.array.foods_pics);
                    @SuppressLint("ResourceType") RowItem item = new RowItem(food_pics.getResourceId(1,-1), rs.getString(2),
                            rs.getFloat(3),rs.getFloat(4),  rs.getFloat(5),rs.getFloat(6));
                    rowItems.add(item);
                }
                mylistview = (ListView) findViewById(R.id.simpleListView);
                CustomAdapter adapter = new CustomAdapter(this,rowItems);
                mylistview.setAdapter(adapter);

                mylistview.setOnItemClickListener(this);
                return mylistview;


//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
//                        getApplicationContext(),
//                        R.layout.activity_list_view,
//                        R.id.result,
//                        listResults
//                );
//                simpleList.setAdapter(arrayAdapter);
//                return simpleList;
            } else {
                ConnectionResult = "Check Connection";
            }
        } catch (Exception ex) {
            Log.e("error", Objects.requireNonNull(ex.getMessage()));
        }
        return simpleList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String food_name = rowItems.get(position).getFood_name();
        Toast.makeText(getApplicationContext(),""+food_name,Toast.LENGTH_SHORT).show();
    }


    public void loadImageByInternetURL(){
        ImageView targetImageView = (ImageView) findViewById(R.id.imageView);
        String internetUrl = "https://pngimg.com/uploads/mario/mario_PNG53.png";

        Picasso
                .with(context)
                .load(internetUrl)
                .into(targetImageView);
    }
}