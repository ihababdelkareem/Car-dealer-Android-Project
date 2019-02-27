package com.example.ihab.labproject;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CustomerMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView profilePic;
    TextView userName;
    private void loadImageFromStorage(String fileName)
    {
        try {
            //File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            ContextWrapper cw = new ContextWrapper(this);
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File f=new File(directory, fileName + ".png");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            profilePic.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
//            Toast.makeText(getActivity(),"No Profile Pic", Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState == null )
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Home()).commit();
            navigationView.setCheckedItem(R.id.ic_home);
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        profilePic = findViewById(R.id.customerProfilePic);
        userName = findViewById(R.id.customerName);
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyData",
                Context.MODE_PRIVATE);
        final String name = sharedPreferences.getString("loggedIn","");
        loadImageFromStorage(name);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this.getApplicationContext());
        Cursor cursor = dataBaseHelper.getUserByEmail(name);
        cursor.moveToNext();
        userName.setText(cursor.getString(3)+" "+cursor.getString(4));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_main, menu);
         profilePic = findViewById(R.id.customerProfilePic);
        userName = findViewById(R.id.customerName);
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyData",
                Context.MODE_PRIVATE);
        final String name = sharedPreferences.getString("loggedIn","");
        loadImageFromStorage(name);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this.getApplicationContext());
        Cursor cursor = dataBaseHelper.getUserByEmail(name);
        cursor.moveToNext();
        userName.setText(cursor.getString(3)+" "+cursor.getString(4));
        // Inflate the layout for this fragment
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        profilePic = findViewById(R.id.customerProfilePic);
        userName = findViewById(R.id.customerName);
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyData",
                Context.MODE_PRIVATE);
        final String name = sharedPreferences.getString("loggedIn","");
        loadImageFromStorage(name);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this.getApplicationContext());
        Cursor cursor = dataBaseHelper.getUserByEmail(name);
        cursor.moveToNext();
        userName.setText(cursor.getString(3)+" "+cursor.getString(4));
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.ic_account)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new User()).commit();
        }
        else if (id == R.id.ic_home)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();

        }
        else if (id == R.id.ic_menu)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CarMenu()).commit();
        }
        else if (id == R.id.ic_add)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Reservations()).commit();
        }
        else if (id == R.id.ic_hot)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SpecialOffers()).commit();
        }
        else if (id == R.id.ic_favorite)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Favorites()).commit();
        }
        else if (id == R.id.ic_location)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Location()).commit();
        }

        else if (id == R.id.ic_exit)
        {
            Intent myIntent=new Intent(CustomerMain.this,
                    LoginActivity.class);
            CustomerMain.this.startActivity(myIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        profilePic = findViewById(R.id.customerProfilePic);
        userName = findViewById(R.id.customerName);
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyData",
                Context.MODE_PRIVATE);
        final String name = sharedPreferences.getString("loggedIn","");
        loadImageFromStorage(name);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this.getApplicationContext());
        Cursor cursor = dataBaseHelper.getUserByEmail(name);
        cursor.moveToNext();
        userName.setText(cursor.getString(3)+" "+cursor.getString(4));
        return true;
    }
}
