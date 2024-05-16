package com.example.idlereasonsproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.idlereasonsproject.FBDatabase.FieldObject;
import com.example.idlereasonsproject.FBDatabase.MachineObject;
import com.example.idlereasonsproject.iface.DataObject;

import java.util.ArrayList;

public class ObjectAdaptor extends ArrayAdapter<DataObject> {

    public ObjectAdaptor(Context context, int resource, ArrayList<DataObject> objectList){
        super(context, resource, objectList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DataObject object = getItem(position);

        if(convertView == null){
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_cell, parent, false);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.text_view);

        tv.setText(object.getName());

        return convertView;
    }
}
