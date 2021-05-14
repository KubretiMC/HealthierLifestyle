package com.mariyan.healthierlifestyle;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

class ConnectionHelper {
    Connection connection = null;
    private String uname,pass,ip,port,database;
    @SuppressLint("NewApi")
    Connection connectionclass()
    {
        ip = "192.168.56.1";
        database="foodsDB";
        uname="sa";
        pass="123456";
        port="1444";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
       // Connection connection = null;
        String ConnectionURL;

        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL= "jdbc:jtds:sqlserver://"+ ip + ":"+ port+";"+ "databasename="+ database+";user="+uname+";password="+pass+";";

            connection= DriverManager.getConnection(ConnectionURL);
        }catch (Exception ex){
            Log.e("Error", Objects.requireNonNull(ex.getMessage()));
        }

        return connection;
    }

    public boolean userCheck(String username, Boolean usernameExists) throws SQLException {

        PreparedStatement st = connectionclass().prepareStatement("select * from users where name = ?");
        st.setString(1, username);
        ResultSet r1=st.executeQuery();
        if(r1.next()) {
            usernameExists = true;
        }
        return usernameExists;
    }
}
