package com.mariyan.healthierlifestyle;

public class RowItem {
    private String food_name;
    private int food_pic_id;
    private float food_proteins;
    private float food_carbohydrates;
    private float food_fats;
    private float food_calories;

    public RowItem(String food_name, int food_pic_id)
    {
        this.food_name = food_name;
        this.food_pic_id=food_pic_id;
    }

    public RowItem(int food_pic_id,String food_name, float food_proteins,float food_carbohydrates,float food_fats, float food_calories)
    {
        this.food_pic_id=food_pic_id;
        this.food_name=food_name;
        this.food_proteins=food_proteins;
        this.food_carbohydrates=food_carbohydrates;
        this.food_fats=food_fats;
        this.food_calories=food_calories;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getFood_pic_id() {
        return food_pic_id;
    }

    public void setFood_pic_id(int food_pic_id) {
        this.food_pic_id = food_pic_id;
    }


    public float getFood_calories() {
        return food_calories;
    }

    public void setFood_calories(float food_calories) {
        this.food_calories = food_calories;
    }

    public float getFood_fats() {
        return food_fats;
    }

    public void setFood_fats(float food_fats) {
        this.food_fats = food_fats;
    }

    public float getFood_carbohydrates() {
        return food_carbohydrates;
    }

    public void setFood_carbohydrates(float food_carbohydrates) {
        this.food_carbohydrates = food_carbohydrates;
    }

    public float getFood_proteins() {
        return food_proteins;
    }

    public void setFood_proteins(float food_proteins) {
        this.food_proteins = food_proteins;
    }
}
