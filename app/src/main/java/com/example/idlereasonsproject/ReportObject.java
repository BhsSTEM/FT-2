package com.example.idlereasonsproject;

import android.util.Log;
import java.util.Calendar;
import java.util.Date;

public class ReportObject {
    String location = "Unknown";
    String machine = "Unknown";
    String reason = "Unknown";
    String furtherInformation = "Blank";
    Date timeOfSubmission;
    public void assignData(String newLocation, String newMachine, String newReason, String newFurtherInformation){
        location = newLocation;
        machine = newMachine;
        reason = newReason;
        furtherInformation = newFurtherInformation;
        timeOfSubmission = Calendar.getInstance().getTime();
        Log.i("Report object", "Data assigned");
    }
    public void reportToLogCat() {
        Log.i("Submission ", "Location: " + location);
        Log.i("Submission", "Machine: " + machine);
        Log.i("Submission", "Reason: " + reason);
        Log.i("Submission", "Further Information: " + furtherInformation);
        Log.i("Submission", "Time of submission: " + timeOfSubmission.toString());
    }
}
