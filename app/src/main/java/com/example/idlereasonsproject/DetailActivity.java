package com.example.idlereasonsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
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
import android.widget.Button;
import android.widget.TextView;

import com.example.idlereasonsproject.FBDatabase.MachineObject;
import com.google.android.material.navigation.NavigationView;

public class DetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    MachineObject selectedShape;
    private AppBarConfiguration appBarConfiguration;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSelectedShape();

       // setValues();
        TextView tv = (TextView) findViewById(R.id.machineName);


        Log.d("DetailActivity", "selected shape:" + selectedShape.getName());
        tv.setText(selectedShape.getName());

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.navigation_drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the action you want when the Button is clicked
                finish(); // Close the current activity and go back to the previous one
            }
        });

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

        tv.setText(selectedShape.getName());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("frgToLoad", item.getItemId());
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp () {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_detail);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

}