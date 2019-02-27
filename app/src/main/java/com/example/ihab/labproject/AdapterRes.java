package com.example.ihab.labproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ihab.labproject.models.Car;

import java.util.ArrayList;

class AdapterRes extends RecyclerView.Adapter<AdapterRes.ListViewHolder> {

    private  ArrayList<Pair<Car,String>> cars;
    Context context;


    public AdapterRes(Context context,ArrayList<Pair<Car,String>> cars){
        this.context = context;
        this.cars = cars;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.reservation_list_item,viewGroup,false);
        return new ListViewHolder(view);
//        return null;
    }

    @Override
    public void onBindViewHolder(ListViewHolder listViewHolder, int i) {
        final int carid = i;
        listViewHolder.textView.setText(cars.get(i).first.getMake()+" " + cars.get(i).first.getModel()+"\n"+cars.get(i).second);
    }
    @Override
    public int getItemCount() {
        return cars.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;

        public ListViewHolder(View itemView){
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.reserve_text_in_list_item);
          //  button1 = (Button)itemView.findViewById(R.id.item_button_1);
         //   button2 = (Button)itemView.findViewById(R.id.item_button_2);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

        }
    }
}