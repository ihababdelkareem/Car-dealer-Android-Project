package com.example.ihab.labproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ihab.labproject.models.Car;

import java.util.ArrayList;
import java.util.List;

public class ConnectionActivity extends AppCompatActivity {

    Button connectButton;
    ArrayList<Car> carListFromRest = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        connectButton = (Button)findViewById(R.id.connectToRestButton);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionAsyncTask connectionAsyncTask= new ConnectionAsyncTask(ConnectionActivity.this);
                connectionAsyncTask.execute("http://www.mocky.io/v2/5bfea5963100006300bb4d9a");
            }
        });
    }

    public void fillCarList(ArrayList<Car> list) {
        if (list == null) {
            Toast.makeText(this, "Error Connecting to API",
                    Toast.LENGTH_LONG).show();
        } else {
            carListFromRest = list;
            DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
            dataBaseHelper.deleteAllCars();
            for (Car car : carListFromRest) dataBaseHelper.insertCar(car);
            Intent myIntent=new Intent(ConnectionActivity.this,
                    LoginActivity.class);
            ConnectionActivity.this.startActivity(myIntent);
            //  dataBaseHelper.getReadableDatabase().delete("CAR",null,null);
//            Cursor cursor = dataBaseHelper.getAllCars();
//            while (cursor.moveToNext()) {
//                System.out.println(cursor.getString(0)+' '+cursor.getString(1)+' '+
//                        cursor.getString(2)+' '+cursor.getString(3)+' '+cursor.getString(4)+' '+
//                        cursor.getString(5)+' '+cursor.getString(6)+' ');
//            }

        }
    }
}
