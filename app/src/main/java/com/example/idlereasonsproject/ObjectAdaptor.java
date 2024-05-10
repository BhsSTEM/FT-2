package com.example.idlereasonsproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.idlereasonsproject.FBDatabase.MachineObject;

import java.util.ArrayList;

public class ObjectAdaptor extends ArrayAdapter<MachineObject> {

    public ObjectAdaptor(Context context, int resource, ArrayList<MachineObject> objectList){
        super(context,resource,objectList);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MachineObject object = getItem(position);

        if(convertView == null){
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.machine_cell, parent, false);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.machineName);

        tv.setText(object.getName());

        return convertView;
    }
}
