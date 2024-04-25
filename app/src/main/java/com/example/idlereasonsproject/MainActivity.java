package com.example.idlereasonsproject;

import android.os.Bundle;

import com.example.idlereasonsproject.FBDatabase.Database;
import com.example.idlereasonsproject.FBDatabase.ReportNode;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;


import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.idlereasonsproject.databinding.ActivityMainBinding;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;


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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml

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
            switch (reportAnalysis.numOfUnresolvedReports(reportAnalysis.reportsFromReporter(Database.getUserLoggedIn().fullName(), ReportNode.getReportMap()))) {
                case 0:
                    Log.i("Report page block", "case 0");
                    navController.navigate(R.id.action_redirect_to_idle);
                    return true;
                case 1:
                    Log.i("Report page block", "case 1");
                    Toast.makeText(this,"You already have a report active, please resolve that before making another report",Toast.LENGTH_LONG).show();
                    return true;
                default:
                    Log.i("Report page block", "default");
                    Log.e("Reports in database", "More than 1 unresolved report (or maybe less than 0 somehow) from user " + Database.getUserLoggedIn().fullName());
                    Toast.makeText(this,"You have more than one report active, this is likely the result of some error",Toast.LENGTH_LONG).show();
                    return true;

            }
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