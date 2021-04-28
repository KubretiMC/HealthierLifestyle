package com.mariyan.healthierlifestyle;
import java.util.ArrayList;
import java.util.List;

public class Food {
    private Integer id;
    private String name;
    private float proteins;
    private float carbohydrates;
    private float fats;
    private float calories;

    public static List<Food> foodsList = new ArrayList<>();

    public Food(Integer id,String name, float proteins, float carbohydrates, float fats, float calories) {
        this.id = id;
        this.name = name;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.calories = calories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getProteins() {
        return proteins;
    }

    public void setProteins(float proteins) {
        this.proteins = proteins;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(float carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public float getFats() {
        return fats;
    }

    public void setFats(float fats) {
        this.fats = fats;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public static List<Food> getList() {
        return foodsList;
    }

    public static void setList(ArrayList<Food> list) {
        Food.foodsList = list;
    }
}
