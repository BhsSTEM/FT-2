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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.idlereasonsproject.FBDatabase.MachineObject;
import com.google.android.material.navigation.NavigationView;

public class DetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    MachineObject selectedShape;
    private AppBarConfiguration appBarConfiguration;

    public DrawerLayout drawerLayout;

    NavController navController;

NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_detail);


        navHostFragment = NavHostFragment.create(R.navigation.nav_graph_detail);

       // TextView tv = (TextView) findViewById(R.id.machineName);


        //Log.d("DetailActivity", "selected shape:" + selectedShape.getName());
        //tv.setText(selectedShape.getName());



        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.just_toolbar);
       // NavigationView navigationView = findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);

        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);

        //drawerLayout.addDrawerListener(toggle);
        //toggle.syncState();


        getSelectedShape();
        setValues();

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DetailActivity", "Back button clicked");
                finish(); // Close the current activity and go back to the previous one
            }
        });

        Button idleReasonButton = findViewById(R.id.idleReasonsButton);
        idleReasonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DetailActivity", "idle reasons button clicked");
                navController.navigate(R.id.action_redirect_to_machine_reason_list);
            }
        });


    }

    //selectedShape seems to be the selected machine
    private void getSelectedShape() {
        Intent previousIntent = getIntent();
        int pos = previousIntent.getIntExtra("pos", -1);
        // Ensure pos is not null and is a valid integer before converting

        if (pos >= 0 && pos < MachineListFragment.machineList.size()) {
            selectedShape = MachineListFragment.machineList.get(pos);
            Log.d("DetailActivity", "selected shape: " + MachineListFragment.machineList.get(pos).getVal() );

        } else {
            Log.e("DetailActivity", "Invalid position: " + pos);
        }
    }


    private void setValues(){
        TextView tv = (TextView) findViewById(R.id.machineName);

        tv.setText("Machine Name: " + selectedShape.getName());

        TextView tv2 = (TextView) findViewById(R.id.machineOperator);
        tv2.setText("Operated By: "+ selectedShape.getOperator());

        TextView tv3 = (TextView) findViewById(R.id.machineTask);
        tv3.setText("Machine Task: "+ selectedShape.getTask());

        TextView tv4 = (TextView) findViewById(R.id.idleOrNot);
        if (machineAnalysis.isMachineIdle(selectedShape)) {
            long idleLength = reportAnalysis.timeBetweenReportAndNow(machineAnalysis.machinesIdleReport(selectedShape));
            tv4.setText(reportAnalysis.idleLength(idleLength, false, selectedShape.getName()));
        }
        else {tv4.setText("Not idle");}

        TextView tv5 = (TextView) findViewById(R.id.idleDetails);
        if (machineAnalysis.isMachineIdle(selectedShape)) {tv5.setText(machineAnalysis.machinesIdleReport(selectedShape).reportText());}
        else {tv5.setText("No idle report to display");}
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("frgToLoad", item.getItemId());
        startActivity(intent);
        return true;
    }
/*
    @Override
    public boolean onSupportNavigateUp () {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_detail);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
*/
}