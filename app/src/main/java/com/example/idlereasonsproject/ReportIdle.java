package com.example.idlereasonsproject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReportIdle extends AppCompatActivity implements OnItemSelectedListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_idle);
// Spinner for fields
        Spinner fieldSpinner = (Spinner) findViewById(R.id.report_idle_field);
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
// Spinner for machines
        Spinner machineSpinner = (Spinner) findViewById(R.id.report_idle_machine);
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
// Spinner for reasons
        Spinner reasonSpinner = (Spinner) findViewById(R.id.report_idle_reason);
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
}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
