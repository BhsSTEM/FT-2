package com.example.idlereasonsproject;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class AppStartUp extends Application {
    public void MyApp() {
        // this method fires only once per application start.
        // getApplicationContext returns null here

        Log.i("main", "Constructor fired");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        // this method fires once as well as constructor
        // but also application has context here

        Log.i("main", "onCreate fired");
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.

        //will need to be changed in the future to have like, variable inputs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Test of Notifications";
            String description = "Test of notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("test", name, importance);
            channel.setDescription(description);
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Log.i("Notifications", "Channel '" + name + "' created");
        }
    }

    public static class ReportObject {
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

        //these types of functions seem to be standard and it can't hurt
        public String getLocation() {return location;}
        public String getMachine(){return machine;}
        public String getReason(){return reason;}
        public String getFurtherInformation(){return furtherInformation;}
        public Date getTimeOfSubmission(){return timeOfSubmission;}
        public String getTimeOfSubmissionAsString(){return timeOfSubmission.toString();}

        //For adding to database, should work when uncommented, maybe this file will need to be placed into FBDatabase I'm not sure
        //import java.util.HashMap;
        //import java.util.Map;
        //Place those with the other imports
        /*public ReportObject(){} // Default constructor required for calls to DataSnapshot.getValue(User.class)*/
        // this might need to go over public class ReportObject {
        /*
        public Map<String, Object> toMap()
        {
            HashMap<String, Object> map  = new HashMap<>();
            map.put("location", location);
            map.put("machine", machine);
            map.put("reason", reason);
            map.put("furtherInformation", furtherInformation);
            map.put("timeOfSubmission", Calendar.getInstance().getTime());

            return map;
        }*/
    }
}
