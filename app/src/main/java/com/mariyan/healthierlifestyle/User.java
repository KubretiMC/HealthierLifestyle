package com.mariyan.healthierlifestyle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class User
{
    private static SharedPreferences mSharedPref;
    private static  String name;
    private static  String age;
    private static  String gender;
    private static  String weight;
    private static  String height;
    private static  String trainings;

    private User()
    {

    }

    public static void init(Context context)
    {
        if(mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    public static String read(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    public static void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public static boolean read(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    public static void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    public static Integer read(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    public static void write(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(key, value).commit();
    }


    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        User.name = name;
    }

    public static String getAge() {
        return age;
    }

    public static void setAge(String age) {
        User.age = age;
    }

    public static String getGender() {
        return gender;
    }

    public static void setGender(String gender) {
        User.gender = gender;
    }

    public static String getWeight() {
        return weight;
    }

    public static void setWeight(String weight) {
        User.weight = weight;
    }

    public static String getHeight() {
        return height;
    }

    public static void setHeight(String height) {
        User.height = height;
    }

    public static String getTrainings() {
        return trainings;
    }

    public static void setTrainings(String trainings) {
        User.trainings = trainings;
    }
}