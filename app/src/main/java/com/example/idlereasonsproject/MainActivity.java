package com.example.idlereasonsproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.idlereasonsproject.FBDatabase.MachineObject;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.idlereasonsproject.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;


//some change :)
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());


        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();


        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


        String[] mList = new String[] {
                "1",  "2",  "3",  "4",  "5",  "6",  "7",  "8",
                "9",  "10", "11", "12", "13", "14", "15", "16",
                "17", "18", "19", "20", "21", "22", "23", "24",
                "25", "26", "27", "28", "29", "30"
        };



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        if (item.getItemId() == R.id.action_tracker) {
            navController.navigate(R.id.action_redirect_to_tracker);
            return true;
        } else if (item.getItemId() == R.id.home_redirect) {
            navController.navigate(R.id.action_redirect_to_home);
            return true;
        } else if (item.getItemId() == R.id.action_machineList) {
            navController.navigate(R.id.action_redirect_to_machine_list);
            return true;
        }
        else if(item.getItemId() == R.id.action_idleReport) {
            navController.navigate(R.id.action_redirect_to_idle);
            return true;
        }
        else if (item.getItemId() == android.R.id.home) {
            navController.navigateUp();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }


    }

        @Override
        public boolean onSupportNavigateUp () {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            return NavigationUI.navigateUp(navController, appBarConfiguration)
                    || super.onSupportNavigateUp();
        }


}