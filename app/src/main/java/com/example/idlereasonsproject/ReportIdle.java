package com.example.idlereasonsproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class ReportIdle extends AppCompatActivity implements OnItemSelectedListener {
    //Variable set up
    String location = "Unknown";
    String machine = "Unknown";
    String reason = "Unknown";
    String furtherInformation = "Blank";
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
//Text box
        TextInputLayout furtherInfoTextBox = (TextInputLayout) findViewById(R.id.report_idle_further_information);
//Submit button
        Button button = (Button) findViewById(R.id.report_idle_button_submit);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                furtherInformation = furtherInfoTextBox.getEditText().getText().toString();
                ReportObject newReport = new ReportObject();
                newReport.assignData(location, machine, reason, furtherInformation);
                newReport.reportToLogCat();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String result = (String) parent.getItemAtPosition(position);
        String chosenSpinner = "placeholder";
        Log.i("Result", result);
        //Recreating adapters, not sure if there's a better way to do this
        ArrayAdapter<CharSequence> fieldAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.fields_array,
                android.R.layout.simple_spinner_item
        );
        ArrayAdapter<CharSequence> machineAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.machines_array,
                android.R.layout.simple_spinner_item
        );
        ArrayAdapter<CharSequence> reasonAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.reasons_array,
                android.R.layout.simple_spinner_item
        );

        //Finding spinner
        int posPlusOne = position + 1;
        if (fieldAdapter.getCount() >= posPlusOne && result == fieldAdapter.getItem(position)) {
            chosenSpinner = "Field";
            Log.i("Chosen Spinner", chosenSpinner);
        } else if (machineAdapter.getCount() >= posPlusOne && result == machineAdapter.getItem(position)) {
            chosenSpinner = "Machine";
            Log.i("Chosen Spinner", chosenSpinner);
        }  else if (reasonAdapter.getCount() >= posPlusOne && result == reasonAdapter.getItem(position)) {
            chosenSpinner = "Reason";
            Log.i("Chosen Spinner", chosenSpinner);
        } else {
            Log.w("Chosen Spinner", "Cannot find chosen spinner");
        }

        //Changing correct variable
        switch (chosenSpinner) {
            case "Field":
                location = result;
                break;
            case "Machine":
                machine = result;
                break;
            case "Reason":
                reason = result;
                break;
            default:
                Log.w("Chosen Spinner", "Value of chosenSpinner does not match possible option" + chosenSpinner);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
