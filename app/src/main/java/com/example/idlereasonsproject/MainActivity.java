package com.example.idlereasonsproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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
    public static ArrayList<MachineObject> machineList = new ArrayList<MachineObject>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getSupportActionBar().setTitle("My title");

        binding = ActivityMainBinding.inflate(getLayoutInflater());


        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
       // setupData();
       // setUpList();
      // setUpOnclickListener();

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




/*
    private void initSearch(){

        SearchView searchView = (SearchView) findViewById(R.id.machine_object_SearchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<MachineObject> filteredObjects = new ArrayList<>();

                for(MachineObject object: machineList){
                    if(object.getName().toLowerCase().contains(s.toLowerCase())){
                        filteredObjects.add(object);
                    }
                }

                ObjectAdaptor adaptor = new ObjectAdaptor(getApplicationContext(), 0, filteredObjects);
                listView.setAdapter(adaptor);



                return false;
            }
        });
    }

*/

    private void setupData(){
        MachineObject combine = new MachineObject("0","john", "Combine");
        machineList.add(combine);

        MachineObject tractor = new MachineObject("1","bob", "Tractor" );
        machineList.add(tractor);

        MachineObject truck = new MachineObject("2","billy", "Truck");
        machineList.add(truck);

    }

    private void setUpList(){
        listView = findViewById(R.id.machine_object_ListView);
        if(listView == null){
            ObjectAdaptor adaptor = new ObjectAdaptor(getApplicationContext(), 0, machineList);
            listView.setAdapter(adaptor);
        }
        else{
            Log.e("MainActivity","List view is null. ");
        }


    }


    private void setUpOnclickListener(){
        Log.d("MainActivity", "ListView value: " + listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MachineObject selectShape = (MachineObject) (listView.getItemAtPosition(position));
                Intent showDetail = new Intent(getApplicationContext(), DetailActivity.class);
                showDetail.putExtra("id",selectShape.getId());
                startActivity(showDetail);
            }
        });
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
/*
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home_direct) {
           NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
          navController.navigate(R.id.action_TrackerFragment_to_HomeFragment);
           return true;
        }

        return super.onOptionsItemSelected(item);
*/

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        //return NavigationUI.onNavDestinationSelected(item, navController)
        //     || super.onOptionsItemSelected(item);
       /*
        if(NavigationUI.onNavDestinationSelected(item, navController)){
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
        action_redirect_to_home
        */

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