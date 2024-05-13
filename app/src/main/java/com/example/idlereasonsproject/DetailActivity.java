package com.example.idlereasonsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idlereasonsproject.FBDatabase.Database;
import com.example.idlereasonsproject.FBDatabase.MachineObject;
import com.example.idlereasonsproject.FBDatabase.ReportNode;
import com.google.android.material.navigation.NavigationView;

import org.checkerframework.checker.units.qual.A;

public class DetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

     MachineObject selectedShape;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavHostFragment navHostFragment;
    NavController navController;
    ActionBar actionBar;

    ActionBarDrawerToggle toggle;
    private AppBarConfiguration appBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_detail);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable back button

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_detail);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);



        getSelectedShape();
        setValues();






    }

    @Override
    public void onStart()
    {
        super.onStart();
//        navController = navHostFragment.getNavController();
        //goingToFrag();
    }
    private void getSelectedShape(){
        Intent previousIntent = getIntent();
        int id = previousIntent.getIntExtra("id", -1);

        Log.d("DetailActivity", "Parsed ID: " + id);
        Log.d("DetailActivity", "Machine List Size: " + MachineListFragment.machineList.size());
        Log.e("MachineListFragment", "machineList size: " + MachineListFragment.machineList.size()); // Log the size of machineList
        for (MachineObject machine : MachineListFragment.machineList) {
            Integer val = machine.getVal();
            Log.d("MachineListFragment", "Machine ID: " + val);
        }
        // Ensure parsedStringId is not null and is a valid integer before converting

            if (id >= 0 && id < MachineListFragment.machineList.size()) {
                selectedShape = MachineListFragment.machineList.get(id);
                Log.d("DetailActivity", "selected shape: " + selectedShape.getVal() );

            } else {
                Log.e("DetailActivity", "Invalid ID: " + id);
            }
        }


    private void setValues(){
        TextView tv = (TextView) findViewById(R.id.machineName);

        tv.setText("Machine Name: " + selectedShape.getName());

        TextView tv2 = (TextView) findViewById(R.id.machineOperator);
        tv2.setText("Operated By: "+ selectedShape.getOperator());

        TextView tv3 = (TextView) findViewById(R.id.machineTask);
        tv3.setText("Machine Task: "+ selectedShape.getTask());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Handle back button click
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onSupportNavigateUp ()
    {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_detail);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


}