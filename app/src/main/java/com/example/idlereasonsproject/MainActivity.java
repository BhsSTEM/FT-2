package com.example.idlereasonsproject;


import android.content.Intent;
import android.os.Bundle;
import com.example.idlereasonsproject.FBDatabase.Database;
import com.example.idlereasonsproject.FBDatabase.ReportNode;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.idlereasonsproject.FBDatabase.Database;
import com.example.idlereasonsproject.FBDatabase.MachineObject;
import com.example.idlereasonsproject.FBDatabase.ReportNode;
import com.example.idlereasonsproject.iface.DrawerLocker;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.idlereasonsproject.databinding.ActivityMainBinding;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import android.view.Window;
import android.widget.Toast;

//some change :)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerLocker
{
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavHostFragment navHostFragment;
    NavController navController;

    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("mainActivity", "fb domain: " + Database.getDomain());
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());


        //create a navigation fragment
        navHostFragment = NavHostFragment.create(R.navigation.nav_graph);

        //replace fragment container in navigation_drawer.xml with nav fragment
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
    }

    @Override
    public void onStart()
    {
        super.onStart();
        navController = navHostFragment.getNavController();
        goingToFrag();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        //TODO: See if this can be replaced with a switch
        int itemId = item.getItemId();
        //check what option was clicked
        if (itemId == R.id.action_tracker)
        {
            navController.navigate(R.id.action_redirect_to_tracker);
        }
        else if (itemId == R.id.home_redirect)
        {
            navController.navigate(R.id.action_redirect_to_home);
        }
        else if (itemId == R.id.action_machineList)
        {
            navController.navigate(R.id.action_redirect_to_machine_list);
        }
        else if(itemId == R.id.action_idleReport)
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
        else if (itemId == android.R.id.home)
        {
            navController.navigateUp();
        }
        else if(item.getItemId() == R.id.action_fieldList)
        {
            navController.navigate(R.id.action_redirect_to_fieldlist);
        }
        else if(item.getItemId() == R.id.action_settings)
        {
            Intent intent = new Intent(getBaseContext(), AccountActivity.class);
            startActivity(intent);

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

        //TODO: See if this can be replaced with a switch
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