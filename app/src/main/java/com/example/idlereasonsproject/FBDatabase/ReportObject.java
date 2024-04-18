package com.example.idlereasonsproject.FBDatabase;

import android.util.Log;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReportObject {
    String location = "";
    String machine = "";
    String reason = "";
    String furtherInformation = "";
    Date timeOfSubmission;
    String reporter = "";

    public ReportObject(){ }

    public ReportObject(String newLocation, String newMachine, String newReason, String newFurtherInformation)
    {
        location = newLocation;
        machine = newMachine;
        reason = newReason;
        furtherInformation = newFurtherInformation;
        timeOfSubmission = Calendar.getInstance().getTime();
        reporter = Database.getUserLoggedIn().getFirstName() + " " + Database.getUserLoggedIn().getLastName();
        Log.i("Report object", "Data assigned " + timeOfSubmission.getTime());
    }

    //these types of functions seem to be standard and it can't hurt
    public String getLocation() {return location;}
    public String getMachine(){return machine;}
    public String getReason(){return reason;}
    public String getFurtherInformation(){return furtherInformation;}
    public Date getTimeOfSubmission(){return timeOfSubmission;}
    public String getReporter() {return reporter;}

    public String reportText() {
        return machine + " at " + location + " is idle because " + reason + ". Further information: " + furtherInformation;
    }
    public Map<String, Object> toMap()
    {
        HashMap<String, Object> map  = new HashMap<>();
        map.put("location", location);
        map.put("machine", machine);
        map.put("reason", reason);
        map.put("furtherInformation", furtherInformation);
        map.put("reporter", reporter);
        map.put("timeOfSubmission", timeOfSubmission);
        return map;
    }
}