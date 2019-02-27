package com.example.ihab.labproject;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class DeleteCustomerAdapter extends RecyclerView.Adapter<DeleteCustomerAdapter.ListViewHolder> {

    private  ArrayList<String > emails;
    Context context;

    public DeleteCustomerAdapter(Context context,ArrayList<String> emails){
        this.context = context;
        this.emails = emails;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_delete_item_list,viewGroup,false);
        return new ListViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ListViewHolder listViewHolder, int i) {
        final DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        final Cursor cursor = dataBaseHelper.getUserByEmail(emails.get(i));
        cursor.moveToNext();
        listViewHolder.textView.setText(cursor.getString(3)+" "+cursor.getString(4));
        listViewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseHelper.deleteByEmail(cursor.getString(0));
                Toast.makeText(context,"Deleted!",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return emails.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        ImageButton imageButton;
        public ListViewHolder(View itemView){
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.text_in_delete_customer_list_item);
            imageButton = (ImageButton)itemView.findViewById(R.id.delete_customer_button_in_list);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

        }
    }
}