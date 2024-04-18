package com.example.idlereasonsproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.idlereasonsproject.databinding.FragmentMachineListBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;


public class MachineListFragment extends Fragment {


    private FragmentMachineListBinding binding;
    private ListView listView;


    public static ArrayList<MachineObject> machineList = new ArrayList<>();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {


        binding = FragmentMachineListBinding.inflate(inflater, container, false);

        setupData();
        setUpList1();
        setUpOnclickListener();







        return binding.getRoot();





    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = binding.machineObjectListView;

        // Retrieve machineList data from MainActivity and update the adapter
        //machineList = MainActivity.machineList;
        setUpList();
        FloatingActionButton addMachineButton = view.findViewById(R.id.addMachineButton);
        addMachineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddMachineDialog(v);
            }
        });
/*
        binding.idleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showIdleDialog();
            }
        });

*/

    }
    private void setupData(){
        MachineObject combine = new MachineObject("0","john", "Combine");
        machineList.add(combine);

        MachineObject tractor = new MachineObject("1","bob", "Tractor" );
        machineList.add(tractor);

        MachineObject truck = new MachineObject("2","billy", "Truck");
        machineList.add(truck);



    }

    private void setUpList1(){
        if(binding != null){
            listView = binding.machineObjectListView;
            if(listView != null){
                ObjectAdaptor adaptor = new ObjectAdaptor(requireContext(), 0, machineList);
                listView.setAdapter(adaptor);

                Log.d("MachineListFragment", "Current machineList: " + machineList.toString());
            }
            else{
                Log.e("MachineListFragment", "list view is null");
            }
        }
        else{
            Log.e("MachineListFragment", "binding is null");
        }


    }


    private void setUpOnclickListener(){
        Log.d("MainActivity", "ListView value: " + listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MachineObject selectShape = (MachineObject) (listView.getItemAtPosition(position));
                Intent showDetail = new Intent(requireContext(), DetailActivity.class);
                showDetail.putExtra("id",selectShape.getId());
                startActivity(showDetail);
            }
        });
    }

    private void setUpList() {
        if (getContext() != null) {
            ObjectAdaptor adapter = new ObjectAdaptor(getContext(), 0, machineList);
            listView.setAdapter(adapter);
        }
    }

    public void showAddMachineDialog(View view) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.add_machine_dialog);

        EditText editTextName = dialog.findViewById(R.id.editTextName);
        EditText editTextType = dialog.findViewById(R.id.editTextType);

        Button addButton = dialog.findViewById(R.id.addButton); // Use existing addButton reference
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String type = editTextType.getText().toString().trim();

                if (!name.isEmpty() && !type.isEmpty()) {
                    // Create a new MachineObject and add it to the list
                    MachineObject newMachine = new MachineObject(name, type);
                    machineList.add(newMachine);

                    // Update the ListView adapter
                    ((ObjectAdaptor) listView.getAdapter()).notifyDataSetChanged();

                    // Dismiss the dialog
                    dialog.dismiss();
                } else {
                    // Show a message if either name or type is empty
                    Toast.makeText(requireContext(), "Please enter both name and type", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }









    // public void showIdleDialog(){
  //      Dialog dialog = new Dialog(requireContext());
    //    dialog.setContentView(R.layout.idle_instances_popup);
     //   dialog.show();
   // }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}