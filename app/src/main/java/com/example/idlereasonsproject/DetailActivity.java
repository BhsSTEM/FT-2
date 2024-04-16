package com.example.idlereasonsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    MachineObject selectedShape;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSelectedShape();
        setValues();

  }

    private void getSelectedShape(){
        Intent previousIntent = getIntent();
        String parsedStringId = previousIntent.getStringExtra("id");
        selectedShape = MachineListFragment.machineList.get(Integer.valueOf(parsedStringId));
    }

    private void setValues(){
        TextView tv = (TextView) findViewById(R.id.machineName);

        tv.setText(selectedShape.getName());
    }

}