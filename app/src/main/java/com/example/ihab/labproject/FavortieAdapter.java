package com.example.ihab.labproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ihab.labproject.models.Car;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class FavortieAdapter extends RecyclerView.Adapter<FavortieAdapter.ListViewHolder> {

    private  ArrayList<Car> cars;
    Context context;


    public FavortieAdapter(Context context,ArrayList<Car> cars){
        this.context = context;
        this.cars = cars;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_list_item,viewGroup,false);
        return new ListViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ListViewHolder listViewHolder, int i) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyData",
                Context.MODE_PRIVATE);
        final String name = sharedPreferences.getString("loggedIn","");
        final int carid = i;
        listViewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                System.out.println(dtf.format(now));
                int newId =0;
                for(int x=0;x<Car.allCars.size();x++){
                    if(cars.get(carid).getModel().equals(Car.allCars.get(x).getModel())) {
                        newId=x;
                        break;
                    }
                }
                dataBaseHelper.insertReserve(name,newId,dtf.format(now));
                System.out.println(name+" "+newId+" "+Car.allCars.get(newId).getModel()+" "+Car.allCars.get(newId).getMake());
            }
        });
       listViewHolder.textView.setText(cars.get(i).getMake()+" " + cars.get(i).getModel());
    }
    @Override
    public int getItemCount() {
        return cars.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        ImageButton imageButton;
        public ListViewHolder(View itemView){
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.car_name_in_fav_list_item);
            imageButton = (ImageButton)itemView.findViewById(R.id.reserve_button_in_fav_list);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

        }
    }
}