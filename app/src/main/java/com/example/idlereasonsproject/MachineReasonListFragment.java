package com.example.idlereasonsproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.idlereasonsproject.FBDatabase.Database;
import com.example.idlereasonsproject.FBDatabase.MachineObject;
import com.example.idlereasonsproject.FBDatabase.ReportObject;
import com.example.idlereasonsproject.databinding.FragmentMachineReasonListBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MachineReasonListFragment extends Fragment
{
    private FragmentMachineReasonListBinding binding;
    private ListView listView;

    //public static ArrayList<MachineObject> machineList = new ArrayList<>();
    public static ArrayList<ReportObject> machineIdleReasons = new ArrayList<>();

    String selectedShapeName = getArguments().getString("selectedShapeName");
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentMachineReasonListBinding.inflate(inflater, container, false);

        listView = binding.machineIdleReasonListView;



        setupData();
       // setUpList();

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        listView = binding.machineIdleReasonListView;

        //put binding check here
    }

    private void setupData(){

        Map<String, ReportObject> reportMap = Database.reportNode.getReportMap();

        for (Map.Entry<String, ReportObject> entry : reportMap.entrySet()) {
            ReportObject reportObject = entry.getValue();

            if(reportObject.getMachine().equals(selectedShapeName)){
                machineIdleReasons.add(reportObject);
            }
        }



        // Notify the adapter that the data set has changed
        if (listView.getAdapter() != null) {
            ((ObjectAdaptor) listView.getAdapter()).notifyDataSetChanged();
        } else {
            Log.e("MachineListFragment", "Adapter is null");
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
