package com.example.idlereasonsproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import java.util.Objects;

public class ReportIdle extends Fragment implements OnItemSelectedListener {
    //Variable set up
    String location = "Unknown";
    String machine = "Unknown";
    String reason = "Unknown";
    String furtherInformation = "Blank";

    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//Field Spinner
        Spinner fieldSpinner = getView().findViewById(R.id.report_idle_field);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> fieldAdapter = ArrayAdapter.createFromResource(
                getActivity(),
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
        Spinner machineSpinner = getView().findViewById(R.id.report_idle_machine);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> machineAdapter = ArrayAdapter.createFromResource(
                getActivity(),
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
        Spinner reasonSpinner = getView().findViewById(R.id.report_idle_reason);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> reasonAdapter = ArrayAdapter.createFromResource(
                getActivity(),
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
        TextInputLayout furtherInfoTextBox = getView().findViewById(R.id.report_idle_further_information);
//Are you sure? Pop up
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    furtherInformation = Objects.requireNonNull(furtherInfoTextBox.getEditText()).getText().toString();
                    ReportObject newReport = new ReportObject();
                    newReport.assignData(location, machine, reason, furtherInformation);
                    newReport.reportToLogCat();
                    //method to send to database
                    //Change to proper home page when everything is merged
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        };
//Submit button
        Button button = getView().findViewById(R.id.report_idle_button_submit);
        button.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String result = (String) parent.getItemAtPosition(position);
        String chosenSpinner = "placeholder";
        Log.i("Result", result);
        //Recreating adapters, not sure if there's a better way to do this
        ArrayAdapter<CharSequence> fieldAdapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.fields_array,
                android.R.layout.simple_spinner_item
        );
        ArrayAdapter<CharSequence> machineAdapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.machines_array,
                android.R.layout.simple_spinner_item
        );
        ArrayAdapter<CharSequence> reasonAdapter = ArrayAdapter.createFromResource(
                getActivity(),
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
