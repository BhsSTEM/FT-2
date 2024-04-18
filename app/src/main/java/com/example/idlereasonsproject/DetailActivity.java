package com.example.idlereasonsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    MachineObject selectedShape;
    private AppBarConfiguration appBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSelectedShape();
        setValues();
       // NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
       // appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();


        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


    }

    private void getSelectedShape(){
        Intent previousIntent = getIntent();
        String parsedStringId = previousIntent.getStringExtra("id");
        selectedShape = MachineListFragment.machineList.get(Integer.valueOf(parsedStringId));
    }

    private void setValues(){
        TextView tv = (TextView) findViewById(R.id.machineName);

        tv.setText(selectedShape.getName());
    }

/*
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

*/




}