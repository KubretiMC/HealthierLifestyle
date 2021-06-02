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
    private Connection connection = null;
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

    public boolean userNameCheck(String username) throws SQLException {

        PreparedStatement st = connectionclass().prepareStatement("select * from users where name = ?");
        st.setString(1, username);
        ResultSet r1=st.executeQuery();
        return r1.next();
    }
    public boolean userCheck(String username, String password) throws SQLException {

        PreparedStatement st = connectionclass().prepareStatement("select * from users where name = ? and password = ?");
        st.setString(1, username);
        st.setString(2,password);
        ResultSet r1=st.executeQuery();
        return r1.next();
    }

    public void userRegister(String username, String password, int age,String gender,int height, int weight,String trainings) throws SQLException {

        PreparedStatement st = connectionclass().prepareStatement("INSERT INTO users (name,password,age,gender,height,weight,trainings) VALUES (?,?,?,?,?,?,?)");
        st.setString(1, username);
        st.setString(2, password);
        st.setInt(3, age);
        st.setString(4, gender);
        st.setInt(5, height);
        st.setInt(6, weight);
        st.setString(7, trainings);
        st.executeQuery();
    }


    public void userUpdate(String username, int age, int height, int weight,String trainings) throws SQLException {

        PreparedStatement st = connectionclass().prepareStatement("UPDATE USERS SET AGE= ?, HEIGHT=?,WEIGHT=?,TRAININGS=? WHERE NAME=?");

        st.setInt(1, age);
        st.setInt(2, height);
        st.setInt(3, weight);
        st.setString(4, trainings);
        st.setString(5, username);
        st.executeUpdate();

    }


    public String[] userGetInfo(String username) throws SQLException {

        PreparedStatement st = connectionclass().prepareStatement("select age,height,weight,trainings from users where name = ?");
        st.setString(1, username);
        ResultSet r1 = st.executeQuery();
        r1.next();
        String[] userInfo = new String[4];
        userInfo[0] = r1.getString("age");
        userInfo[1] = r1.getString("height");
        userInfo[2] = r1.getString("weight");
        userInfo[3] = r1.getString("trainings");
        return userInfo;
    }
}
