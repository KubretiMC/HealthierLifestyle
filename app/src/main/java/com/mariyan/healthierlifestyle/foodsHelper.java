package com.mariyan.healthierlifestyle;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;



public class foodsHelper extends SQLiteOpenHelper {
    private static final String DATABASE_PATH ="/data/data/com.mariyan.healthierlifestyle/databases/";
    private static final String DATABASE_NAME = "foods.db";
    private static final int SCHEMA_VERSION=1;
    public static final String TABLE_NAME = "foods";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PROTEINS = "proteins";
    public static final String COLUMN_CARBOHYDRATES = "carbohydrates";
    public static final String COLUMN_FAT = "fat";
    public static final String COLUMN_CALORIES = "calories";

    public SQLiteDatabase dbSQLite;
    private final Context myContext;

    public foodsHelper(Context context){
        super(context,DATABASE_NAME,null,SCHEMA_VERSION);
        this.myContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createDatabase(){
        createDB();
    }

    private void createDB(){
        boolean dbExist = DBExists();
        if(!dbExist){
            this.getReadableDatabase();
            copyDBFromResource();
        }
    }

    private boolean DBExists(){
        SQLiteDatabase db =null;
        try{
            String databasePath=DATABASE_PATH+DATABASE_NAME;
            db = SQLiteDatabase.openDatabase(databasePath,null,SQLiteDatabase.OPEN_READWRITE);
            db.setLocale(Locale.getDefault());
            db.setLockingEnabled(true);
            db.setVersion(1);
        }catch (SQLException e) {
            Log.e("SqlHelper","database not found");
        }

        if(db != null){
            db.close();
        }

        return db !=null?true:false;
    }

    private void copyDBFromResource(){
        InputStream inputStream = null;
        OutputStream outStream = null;
        String dbFilePath =DATABASE_PATH+DATABASE_NAME;

        try{
            inputStream = myContext.getAssets().open(DATABASE_NAME);
            outStream = new FileOutputStream(dbFilePath);
            byte[] buffer = new byte[1024];
            int lenght;
            while((lenght=inputStream.read(buffer))>0){
                outStream.write(buffer,0,lenght);
            }
            outStream.flush();
            outStream.close();
            inputStream.close();
        } catch (IOException e){
            throw new Error("Probem copying database from resource file.");
        }
    }

    public void openDataBase() throws SQLException {
        String myPath = DATABASE_PATH+DATABASE_NAME;
        dbSQLite =SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close(){
        if(dbSQLite != null)
        {
            dbSQLite.close();
        }
        super.close();
    }

    public Cursor getCursor(){
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);
        String[] asColumnsToReturn = new String[] {COLUMN_ID,COLUMN_NAME,COLUMN_PROTEINS,
                COLUMN_CARBOHYDRATES,COLUMN_FAT,COLUMN_CALORIES};
        Cursor mCursor = queryBuilder.query(dbSQLite,asColumnsToReturn,null,null,
                null,null,null);
        return mCursor;
    }
    public String getName(Cursor c){
        return(c.getString(1));
    }
}
