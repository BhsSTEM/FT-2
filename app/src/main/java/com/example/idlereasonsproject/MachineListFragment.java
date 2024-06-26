package com.example.idlereasonsproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.idlereasonsproject.FBDatabase.Database;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.idlereasonsproject.FBDatabase.MachineObject;
import com.example.idlereasonsproject.databinding.FragmentMachineListBinding;
import com.example.idlereasonsproject.iface.DataObject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Map;

public class MachineListFragment extends Fragment {
    private FragmentMachineListBinding binding;
    private ListView listView;
    private int blahCounter = 0;


    public static ArrayList<MachineObject> machineList = new ArrayList<>();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentMachineListBinding.inflate(inflater, container, false);
        listView = binding.machineObjectListView;

        setupData();
        setUpList();

        setUpOnclickListener();

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = binding.machineObjectListView;

        //setUpList();
        FloatingActionButton addMachineButton = view.findViewById(R.id.addMachineButton);
        addMachineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddMachineDialog(v);
            }
        });

    }

    private void setupData(){

        Map<String, MachineObject> machineMap = Database.machineNode.getMachineMap();
        Log.e("MachineListFragment", "machineMap size: " + machineMap.size()); // Log the size of machineMap

        machineList.clear();

        //For testing
        /*ArrayList<MachineObject> sortedMachineList = machineAnalysis.alphabeticallySortMachineList(machineMap);
        for (int i = 0; i < sortedMachineList.size(); i++) {
            machineList.add(sortedMachineList.get(i));
            Log.i("sortedMachineList", sortedMachineList.get(i).getName());
        }*/

        // Add values from machineMap to machineList
        for (Map.Entry<String, MachineObject> entry : machineMap.entrySet()) {
            MachineObject machineObject = entry.getValue();
            Log.e("MachineListFragment", "Adding machine: " + machineObject.getName()); // Log each machine being added
            machineList.add(machineObject);
        }

        Log.e("MachineListFragment", "machineList size: " + machineList.size()); // Log the size of machineList
        for (MachineObject machine : machineList) {
            Integer id = machine.getVal();
            Log.d("MachineListFragment", "Machine ID: " + id);
        }
        // Notify the adapter that the data set has changed
        if (listView.getAdapter() != null) {
            ((ObjectAdaptor) listView.getAdapter()).notifyDataSetChanged();
        } else {
            Log.e("MachineListFragment", "Adapter is null");
        }
    }

    private void setUpOnclickListener(){
        Log.d("MainActivity", "ListView value: " + listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showDetail = new Intent(requireContext(), DetailActivity.class);
                showDetail.putExtra("pos", position);
                startActivity(showDetail);
            }
        });
    }

    private void setUpList() {
        if (getContext() != null) {
            ObjectAdaptor adapter = new ObjectAdaptor(getContext(), 0, machineListToDataObject());
            listView.setAdapter(adapter);
        }
    }

    public void showAddMachineDialog(View view) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.add_machine_dialog);

        EditText editTextName = dialog.findViewById(R.id.editTextName);
        EditText editTextType = dialog.findViewById(R.id.editTextType);
        EditText editTextOperator = dialog.findViewById(R.id.editTextOperatorName);
        EditText editTextMachineTask = dialog.findViewById(R.id.editTextMachineTask);

        Button addButton = dialog.findViewById(R.id.addButton); // Use existing addButton reference
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String type = editTextType.getText().toString().trim();
                String operator = editTextOperator.getText().toString().trim();
                String task = editTextMachineTask.getText().toString().trim();

                Map<String, MachineObject> machineMap = Database.machineNode.getMachineMap();
                Log.e("MachineListFragment", "machineMap size: " + machineMap.size());
                Integer newId = machineMap.size();

                if (!name.isEmpty() && !type.isEmpty()) {
                    // Create a new MachineObject and add it to the list

                    Log.d("MachineListFragment", "Assigning new ID: " + newId + " to machine: " + name);
                    MachineObject newMachine = new MachineObject(newId, name, type, operator, task);
                    machineList.add(newMachine);
                    Log.d("MachineListFragment", "Current machineList: " + machineList.toString());

                    Database.machineNode.addMachine(newMachine);

                    // Update the ListView adapter
                    ((ObjectAdaptor) listView.getAdapter()).notifyDataSetChanged();

                    // Dismiss the dialog
                    dialog.dismiss();
                } else {
                    // Show a message if either name or type is empty
                    Toast.makeText(requireContext(), "Please enter both name, type and operator", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private ArrayList<DataObject> machineListToDataObject()
    {
        return new ArrayList<>(machineList);
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