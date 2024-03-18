package com.example.idlereasonsproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReportIdle extends AppCompatActivity implements OnItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_idle);

//Field Spinner
        Spinner fieldSpinner = findViewById(R.id.report_idle_field);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> fieldAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.fields_array,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        fieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        fieldSpinner.setAdapter(fieldAdapter);
        fieldSpinner.setOnItemSelectedListener(this);
        Log.i("My Tag", "Field spinner up");

//Machine Spinner
        Spinner machineSpinner = findViewById(R.id.report_idle_machine);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> machineAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.machines_array,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        machineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        machineSpinner.setAdapter(machineAdapter);
        machineSpinner.setOnItemSelectedListener(this);
        Log.i("My Tag", "Machine spinner up");
//Reason Spinner
        Spinner reasonSpinner = findViewById(R.id.report_idle_reason);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> reasonAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.reasons_array,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        reasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        reasonSpinner.setAdapter(reasonAdapter);
        reasonSpinner.setOnItemSelectedListener(this);
        Log.i("My Tag", "Reason spinner up");

//Variable set up
        String location = "Unknown";
        String machine = "Unknown";
        String reason = "Unknown";
        String furtherInformation = "Blank";
        Log.i("My Tag", "Variables set up");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String result = (String) parent.getItemAtPosition(position);
        Log.i("My Tag", result);
        //Best way to figure out which variables to change is probably a switch statment
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
