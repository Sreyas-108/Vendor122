package com.example.abhishek.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by abhishek on 21/10/18.
 */

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {


    public List<UserModel> userModelList;

    public UsersListAdapter(List<UserModel> userModelList){
        this.userModelList = userModelList;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.nameText.setText(userModelList.get(position).getCustomer());
        holder.statusText.setText(userModelList.get(position).getStatus());

        if((holder.statusText.getText()+"").equals("Finished")||(holder.statusText.getText()+"").equals("Accepted"))
        {holder.statusText.setTextColor(Color.parseColor("#008000"));}


        if((holder.statusText.getText()+"").equals("Pending")||(holder.statusText.getText()+"").equals("Declined"))
        {holder.statusText.setTextColor(Color.parseColor("#FF0000"));}


        if((holder.statusText.getText()+"").equals("Ready"))
        {holder.statusText.setTextColor(Color.parseColor("#008000"));}

        holder.otp.setText("OTP: " +userModelList.get(position).getotp());
        holder.otp.setTextColor(Color.parseColor("#3F51B5"));
        holder.order_id.setText( userModelList.get(position).getId()+"");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.mView.getContext() , ItemDisplay.class);
                intent.putExtra("ID", userModelList.get(position).id);
                intent.putExtra("order_status",userModelList.get(position).status);
                holder.mView.getContext().startActivity(intent);
            }
        });
        Log.d(TAG , "name"+userModelList.get(position).getCustomer());

    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

       View mView;
       public TextView nameText;
       public TextView statusText;
       public  TextView otp;
       public TextView order_id;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            nameText = (TextView)mView.findViewById(R.id.name_text);
            statusText = (TextView)mView.findViewById(R.id.status_text);
            otp = (TextView)mView.findViewById(R.id.otp);
            order_id = (TextView)mView.findViewById(R.id.order_id);

        }
    }
}
