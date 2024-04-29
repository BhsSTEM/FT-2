package com.example.idlereasonsproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.idlereasonsproject.FBDatabase.Database;
import com.example.idlereasonsproject.FBDatabase.MachineObject;
import com.example.idlereasonsproject.FBDatabase.ReportObject;
import com.example.idlereasonsproject.databinding.ReportIdleBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class ReportIdleFragment extends Fragment implements OnItemSelectedListener {
    private ReportIdleBinding binding;
    //Variable set up
    String location = "";
    String machine = "";
    String reason = "";
    String furtherInformation = "";

    Map<String, MachineObject> machineMap = Database.machineNode.getMachineMap();
    ArrayList<String> machineList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = ReportIdleBinding.inflate(inflater, container,false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

        //Creating an array to use for this specfic spinner, the goal is to have this in the future be pulled from somewhere else instead of just created here
        //Idealy, in the future, if the user is marked as using a machine that one would pop up first, and if they're using multiple, those would pop up first

        for(MachineObject machine: machineMap.values())
        {
            machineList.add(machine.getName());
        }

        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<String> machineAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, machineList.toArray());

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
                    ReportObject report = new ReportObject(location, machine, reason, furtherInformation);

                    //method to send to database
                    Database.reportNode.addReportToDB(report);

                    NavHostFragment.findNavController(ReportIdleFragment.this)
                            .navigate(R.id.action_ReportIdle_to_HomeFragment);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        };

        //Submit button
        Button button = getView().findViewById(R.id.report_idle_button_submit);
        button.setOnClickListener(v -> {
            //add user check to make sure they don't have empty boxes

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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

        ArrayAdapter<String> machineAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, machineList);
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
        } else if (machineAdapter.getCount() >= posPlusOne && result.equals(machineAdapter.getItem(position))) {
            chosenSpinner = "Machine";
            Log.v("Chosen Spinner", chosenSpinner+"\n" + machineAdapter+ " " + machineAdapter.getCount());
        }  else if (reasonAdapter.getCount() >= posPlusOne && result == reasonAdapter.getItem(position)) {
            chosenSpinner = "Reason";
            Log.i("Chosen Spinner", chosenSpinner);
        } else {
            Log.w("Chosen Spinner", "Cannot find chosen spinner"+"\n" + machineAdapter+ " " + machineAdapter.getCount());
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
    public void onNothingSelected(AdapterView<?> parent)
    {}
}