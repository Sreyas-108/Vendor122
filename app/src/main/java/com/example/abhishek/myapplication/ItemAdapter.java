package com.example.abhishek.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by abhishek on 22/10/18.
 */



public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder1> {

    public List<ItemModel> itemModelList;
    public ItemAdapter(List<ItemModel> itemModelList){
        this.itemModelList = itemModelList;

    }

    @Override
    public ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.final_list_item , parent , false);

        return new ViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder1 holder, int position) {
        holder.final_list_name.setText(itemModelList.get(position).getName());
        holder.final_list_quantity.setText(itemModelList.get(position).getQty() + "");


    }

    @Override
    public int getItemCount() {
        return itemModelList.size();
    }

    static class ViewHolder1 extends RecyclerView.ViewHolder {

        View mView1;
        TextView final_list_name , final_list_quantity;
        ViewHolder1(View view) {
            super(view);
            mView1= view;
            final_list_name= (TextView)mView1.findViewById(R.id.final_item_name);
            final_list_quantity=(TextView)mView1.findViewById(R.id.final_item_qty);
        }
    }
}