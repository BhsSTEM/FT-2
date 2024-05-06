package com.example.idlereasonsproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.idlereasonsproject.FBDatabase.Database;
import com.example.idlereasonsproject.FBDatabase.FieldObject;
import com.example.idlereasonsproject.databinding.FragmentFieldListBinding;

import java.util.ArrayList;
import java.util.Map;

public class FieldListFragment extends Fragment
{

    private FragmentFieldListBinding binding;
    private ListView listView;

    private ArrayList<FieldObject> fieldList = new ArrayList<>();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFieldListBinding.inflate(inflater, container, false);
        listView = binding.fieldListView;

        //populate field list with stuff from firebase
        fieldList.addAll(Database.fieldNode.getFieldMap().values());
        Log.v("fieldList", fieldList.toString());

        setFieldListView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        //setup list
        //setup buttons
        setUpOnClickListener();
        binding.addFieldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(FieldListFragment.this)
                        .navigate(R.id.action_FieldList_to_CreateField);
            }
        });
    }

    private void setFieldListView()
    {
        String[] fieldNames = new String[fieldList.size()];

        for(int i=0; i<fieldList.size(); i++)
        {
            fieldNames[i] = fieldList.get(i).getName();
        }

        ArrayAdapter<String> fieldAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, fieldNames);

        listView.setAdapter(fieldAdapter);
    }

    private void setUpOnClickListener()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //FieldObject fieldSelected = (FieldObject)(listView.getItemAtPosition(position));
                Log.v("fieldList", "Item selected " + listView.getItemAtPosition(position).toString());
                //later switch to a field detail object
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

}
