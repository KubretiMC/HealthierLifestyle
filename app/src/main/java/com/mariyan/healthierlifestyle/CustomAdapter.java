package com.mariyan.healthierlifestyle;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter implements Filterable {

    private List<RowItem> rowItems;
    private List<RowItem> rowItemsListFiltered;
    private Context context;

    CustomAdapter(List<RowItem> rowItems, Context context) {
        this.rowItems = rowItems;
        this.rowItemsListFiltered=rowItems;
        this.context = context;
    }


    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_items, null);
            holder = new ViewHolder();
            holder.food_name = (TextView) convertView
                    .findViewById(R.id.itemName2);
            holder.food_pic = (ImageView) convertView
                    .findViewById(R.id.imageView);
            holder.food_proteins = (TextView) convertView
                    .findViewById(R.id.itemProteins2);
            holder.food_carbohydrates = (TextView) convertView
                    .findViewById(R.id.itemCarbohydrates2);
            holder.food_fats = (TextView) convertView
                    .findViewById(R.id.itemFats2);
            holder.food_calories = (TextView) convertView
                    .findViewById(R.id.itemCalories2);


            RowItem row_pos = rowItems.get(position);

            holder.food_pic.setImageResource(row_pos.getFood_pic_id());
            holder.food_name.setText(row_pos.getFood_name());
            holder.food_proteins.setText(String.valueOf(row_pos.getFood_proteins()));
            holder.food_carbohydrates.setText(String.valueOf(row_pos.getFood_carbohydrates()));
            holder.food_fats.setText(String.valueOf(row_pos.getFood_fats()));
            holder.food_calories.setText(String.valueOf(row_pos.getFood_calories()));

        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = rowItems.size();
                    filterResults.values = rowItems;

                } else {
                    List<RowItem> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (RowItem rowItem : rowItems) {
                        if (rowItem.getFood_name().contains(searchStr)) {
                            resultsModel.add(rowItem);
                            filterResults.count = resultsModel.size();
                            filterResults.values = resultsModel;
                        }
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                rowItemsListFiltered = (List<RowItem>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }



    private class ViewHolder {
        ImageView food_pic;
        TextView food_name;
        TextView food_proteins;
        TextView food_carbohydrates;
        TextView food_fats;
        TextView food_calories;
    }
}
