package com.example.idlereasonsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.idlereasonsproject.FBDatabase.MachineObject;
import com.example.idlereasonsproject.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    MachineObject selectedShape;
    private AppBarConfiguration appBarConfiguration;

    private ActivityDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_detail);
        //binding = ActivityDetailBinding.inflate(getLayoutInflater());
       // setContentView(binding.getRoot());
        //setSupportActionBar(binding.toolbar);
    setContentView(R.layout.activity_detail);
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_detail);

       // appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();


      //  NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        getSelectedShape();
        setValues();
    }


    private void getSelectedShape(){
        Intent previousIntent = getIntent();
        String parsedStringId = previousIntent.getStringExtra("id");

        Log.d("DetailActivity", "Parsed ID: " + parsedStringId);
        Log.d("DetailActivity", "Machine List Size: " + MachineListFragment.machineList.size());
        // Ensure parsedStringId is not null and is a valid integer before converting
        if (parsedStringId != null && !parsedStringId.isEmpty()) {
            int id = Integer.parseInt(parsedStringId);
            // Check if the ID is within the bounds of the list
            if (id >= 0 && id < MachineListFragment.machineList.size()) {
                selectedShape = MachineListFragment.machineList.get(id);
            } else {
                Log.e("DetailActivity", "Invalid ID: " + id);
            }
        } else {
            Log.e("DetailActivity", "Parsed ID is null or empty");
        }
    }

    private void setValues(){
      //  TextView tv = (TextView) findViewById(R.id.machineName);
       // TextView tv = binding.machineName;
        TextView tv = (TextView) findViewById(R.id.machineName);

        tv.setText(selectedShape.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_tracker) {
            // Handle action_tracker menu item click
            return true;
        } else if (id == R.id.home_redirect) {
            // Handle home_redirect menu item click
            return true;
        } else if (id == R.id.action_machineList) {
            // Handle action_machineList menu item click
            return true;
        } else if (id == R.id.action_idleReport) {
            // Handle action_idleReport menu item click
            return true;
        } else if (id == android.R.id.home) {
            // Handle home menu item click
            onBackPressed(); // This will simulate the behavior of the back button
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp () {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_detail);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

}