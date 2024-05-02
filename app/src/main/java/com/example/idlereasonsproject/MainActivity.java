package com.example.idlereasonsproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;


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
import com.example.idlereasonsproject.iface.DrawerLocker;
import com.google.android.material.navigation.NavigationView;
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
        else if(item.getItemId() == R.id.action_idleReport)
        {
            navController.navigate(R.id.action_redirect_to_idle);
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
            navController.navigate(R.id.action_redirect_to_idle);
        }
        else
        {
            navController.navigate(R.id.action_redirect_to_home);
        }
    }



}