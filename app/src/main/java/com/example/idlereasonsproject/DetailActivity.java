package com.example.idlereasonsproject;

import androidx.annotation.NonNull;
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

public class DetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

     MachineObject selectedShape;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavHostFragment navHostFragment;
    NavController navController;

    ActionBarDrawerToggle toggle;
    private AppBarConfiguration appBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail);

        getSelectedShape();
        setValues();




        navHostFragment = NavHostFragment.create(R.navigation.nav_graph);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, navHostFragment)
                .setPrimaryNavigationFragment(navHostFragment)
                .commit();


        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.navigation_drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        Button idleReasonButton = findViewById(R.id.idleReasonsButton);
        idleReasonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("selectedShapeName", selectedShape.getName());

                navController.navigate(R.id.action_redirect_to_machine_reason_list);
            }
        });


    }

    @Override
    public void onStart()
    {
        super.onStart();
        navController = navHostFragment.getNavController();
        goingToFrag();
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
        //check what option was clicked
        if (item.getItemId() == R.id.action_tracker)
        {
            navController.navigate(R.id.action_redirect_to_tracker);
        }
        else if (item.getItemId() == R.id.home_redirect)
        {
            navController.navigate(R.id.action_redirect_to_home);
        }
        else if (item.getItemId() == R.id.action_machineList)
        {
            navController.navigate(R.id.action_redirect_to_machine_list);
        }
        else if(item.getItemId() == R.id.action_idleReport) // fix this
        {
            switch (reportAnalysis.numOfUnresolvedReports(reportAnalysis.reportsFromReporter(Database.getUserLoggedIn().fullName(), ReportNode.getReportMap()))) {
                case 0:
                    Log.i("Report page block", "case 0");
                    navController.navigate(R.id.action_redirect_to_idle);
                    break;
                case 1:
                    Log.i("Report page block", "case 1");
                    Toast.makeText(this, "You already have a report active, please resolve that before making another report", Toast.LENGTH_LONG).show();
                    break;
                default:
                    Log.i("Report page block", "default");
                    Log.e("Reports in database", "More than 1 unresolved report (or maybe less than 0 somehow) from user " + Database.getUserLoggedIn().fullName());
                    Toast.makeText(this, "You have more than one report active, this is likely the result of some error", Toast.LENGTH_LONG).show();
                    break;
            }
        }
        else if (item.getItemId() == android.R.id.home)
        {
            navController.navigateUp();
        }
        else if(item.getItemId() == R.id.action_fieldList)
        {
            navController.navigate(R.id.action_redirect_to_fieldlist);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp ()
    {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //method to set navigation drawer to be enabled or not
    public void setDrawerEnabled(boolean enabled)
    {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawerLayout.setDrawerLockMode(lockMode);
        toggle.setDrawerIndicatorEnabled(enabled);
    }

    //method to check if we are going to a specific fragment when the activity is created
    private void goingToFrag()
    {

        //statement to check if its going back to the default fragment(domain fragment)
        if(getIntent().getExtras() == null)
        {
            return;
        }

        int intentFragId = getIntent().getExtras().getInt("frgToLoad");

        //check what option was clicked
        if (intentFragId == R.id.action_tracker)
        {
            navController.navigate(R.id.action_redirect_to_tracker);
        }
        else if (intentFragId == R.id.home_redirect)
        {
            navController.navigate(R.id.action_redirect_to_home);
        }
        else if (intentFragId == R.id.action_machineList)
        {
            navController.navigate(R.id.action_redirect_to_machine_list);
        }
        else if(intentFragId == R.id.action_idleReport)
        {
            switch (reportAnalysis.numOfUnresolvedReports(reportAnalysis.reportsFromReporter(Database.getUserLoggedIn().fullName(), ReportNode.getReportMap()))) {
                case 0:
                    Log.i("Report page block", "case 0");
                    navController.navigate(R.id.action_redirect_to_idle);
                    break;
                case 1:
                    Log.i("Report page block", "case 1");
                    Toast.makeText(this,"You already have a report active, please resolve that before making another report",Toast.LENGTH_LONG).show();
                    break;
                default:
                    Log.i("Report page block", "default");
                    Log.e("Reports in database", "More than 1 unresolved report (or maybe less than 0 somehow) from user " + Database.getUserLoggedIn().fullName());
                    Toast.makeText(this,"You have more than one report active, this is likely the result of some error",Toast.LENGTH_LONG).show();
                    break;

            }
        }
        else
        {
            navController.navigate(R.id.action_redirect_to_home);
        }
    }
}