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

class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private  ArrayList<Car> cars;
    Context context;


    public ListAdapter(Context context,ArrayList<Car> cars){
        this.context = context;
        this.cars = cars;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_car_type,viewGroup,false);
        return new ListViewHolder(view);
//        return null;
    }

    @Override
    public void onBindViewHolder(ListViewHolder listViewHolder, int i) {
        final int carid = i;
                    SharedPreferences sharedPreferences = context.getSharedPreferences("MyData",
                    Context.MODE_PRIVATE);
            final String name = sharedPreferences.getString("loggedIn","");
            String details = "";
            details+="Year: "+cars.get(i).getYear()+"\n";
        details+="Make: "+cars.get(i).getMake()+"\n";
        details+="Model: "+cars.get(i).getModel()+"\n";
        details+="Distance: "+cars.get(i).getDistance()+"\n";
        details+="Price: "+cars.get(i).getPrice()+"\n";
        //System.out.println(details);
        listViewHolder.textView.setText(details);
        //listViewHolder.textView.setText("AAA");
        listViewHolder.button1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                System.out.println(dtf.format(now));
                dataBaseHelper.insertReserve(name,carid,dtf.format(now));
              //  System.out.println("RES: " + name+" -> " + carid);
            }
        });
        listViewHolder.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
                dataBaseHelper.insertFavorite(name,carid);
               // System.out.println("Fav: " + name+" -> " + carid);

            }
        });
    }
    @Override
    public int getItemCount() {
        return cars.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        public ImageButton button1;
        public ImageButton button2;
        public ListViewHolder(View itemView){
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.details_text_menu);
            button2 = (ImageButton) itemView.findViewById(R.id.fav_button_menu);
            button1 = (ImageButton) itemView.findViewById(R.id.reserve_button_menu);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
        }
    }
}
