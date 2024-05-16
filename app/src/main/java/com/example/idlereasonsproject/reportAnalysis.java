package com.example.idlereasonsproject;

import android.util.Log;
import com.example.idlereasonsproject.FBDatabase.ReportNode;
import com.example.idlereasonsproject.FBDatabase.ReportObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
public class reportAnalysis {
//Logging
    /**
     * prints the result of reportText() for each ReportObject in a hashmap to logcat
     */
    public static void reportObjectHashmapToLogCat(Map<String, ReportObject> map) {
        for (Map.Entry<String, ReportObject> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().reportText();
            Log.i("reportMap", "Key=" + key + ", Value=" + value);
        }
    }
    /**
     * does the same as reportObjectHashmapToLogCat but with the full report map
     */
    public static void reportMapReportTextToLogCat() {
        reportObjectHashmapToLogCat(ReportNode.getReportMap());
    }
//Time related functions
    /**
     * Returns a string that says how long a machine was/has been idle for. Probably should be rewritten to have less inputs
     */
    public static String idleLength (long lengthValue, boolean resolvedOrNot, String machine) {
        lengthValue = lengthValue - (lengthValue % 1000);
        long numOfSeconds = lengthValue/1000;
        long numOfMinutes = (numOfSeconds - (numOfSeconds%60))/60;
        numOfSeconds = numOfSeconds%60;
        long numOfHours = (numOfMinutes - (numOfMinutes%60))/60;
        numOfMinutes = numOfMinutes%60;
        String returnedString ="";
        if (resolvedOrNot) {returnedString = machine + " was idle for ";}
        else {returnedString = machine + " has been idle for ";}
        if (numOfHours != 0) {
            if (numOfHours == 1) {
                returnedString = returnedString + "1 hour, ";
            }
            else {
                returnedString = returnedString + numOfHours + " hours, ";
            }
        }
        if (numOfMinutes != 0) {
            if (numOfSeconds == 0) {returnedString = returnedString + "and ";}
            if (numOfMinutes == 1) {
                returnedString = returnedString + "1 minute, ";
            }
            else {
                returnedString = returnedString + numOfMinutes + " minutes, ";
            }
        }
        if (numOfSeconds != 0) {
            if (numOfHours != 0 || numOfMinutes != 0) {
                returnedString = returnedString + "and ";
            }
            if (numOfSeconds == 1) {
                returnedString = returnedString + "1 second.";
            }
            else {
                returnedString = returnedString + numOfSeconds + " seconds.";
            }
        }
        return returnedString;
    }
    /**
     * Returns the time (in milliseconds) between when the report was filed and when it was resolved.
     * Assumes the report has been resolved, otherwise it'll return a negative value which would be weird.*/
    public static long timeBetweenReportAndResolution(ReportObject report) {
        return report.getTimeOfResolution().getTime() - report.getTimeOfSubmission().getTime();
    }

    /**
     * Returns the time (in milliseconds) between when the report was filed and now*/
    public static long timeBetweenReportAndNow(ReportObject report) {
        Date now = new Date();
        return now.getTime() - report.getTimeOfSubmission().getTime();
    }
//Reports from a reporter
    /**
     * Returns how many reports have come from the inputted reporter, which I think should be the reporter's name, in lowercase
     */
    public static int numOfReportsFromReporter(String reporter, Map<String, ReportObject> map) {
        int numOfReports = 0;
        for (Map.Entry<String, ReportObject> entry : map.entrySet()) {
            String valueOfReporter = entry.getValue().getReporter();
            if (reporter.equals(valueOfReporter)){
                numOfReports++;
            }
        }
        return numOfReports;
    }
    /**
     * Returns a hashmap of reports that have come from the inputted reporter, which I think should be the reporter's name, in lowercase
     */
    public static Map<String, ReportObject> reportsFromReporter(String reporter, Map<String, ReportObject> map) {
        Map<String, ReportObject> returnedMap = new HashMap<>();
        for (Map.Entry<String, ReportObject> entry : map.entrySet()) {
            String key = entry.getKey();
            ReportObject value = entry.getValue();
            if (reporter.equals(value.getReporter())){
                returnedMap.put(key, value);
            }
        }
        return returnedMap;
    }
//Reports from a machine
    /**
     * Returns how many reports have come from the inputted machine, which I think should just be the machine's name
     */
    public static int numOfReportsFromMachine(String machine, Map<String, ReportObject> map) {
        int numOfReports = 0;
        for (Map.Entry<String, ReportObject> entry : map.entrySet()) {
            String valueOfMachine = entry.getValue().getMachine();
            if (machine.equals(valueOfMachine)){
                numOfReports++;
            }
        }
        return numOfReports;
    }
    /**
     * Returns a hashmap of reports that have come from the inputted machine, which I think should be the machine's name
     */
    public static Map<String, ReportObject> reportsFromMachine(String machine, Map<String, ReportObject> map) {
        Map<String, ReportObject> returnedMap = new HashMap<>();
        for (Map.Entry<String, ReportObject> entry : map.entrySet()) {
            String key = entry.getKey();
            ReportObject value = entry.getValue();
            if (machine.equals(value.getMachine())){
                returnedMap.put(key, value);
            }
        }
        return returnedMap;
    }
//Unresolved reports
    /**
     * Returns how many reports there are that are unresolved
     */
    public static int numOfUnresolvedReports(Map<String, ReportObject> map) {
        int numOfReports = 0;
        for (Map.Entry<String, ReportObject> entry : map.entrySet()) {
            if (!entry.getValue().isResolved()) {
                numOfReports++;
            }
            Log.i("numOfUnresolvedReports", String.valueOf(numOfReports));
        }
        return numOfReports;
    }
    /**
     * Returns a hashmap of unresolved reports
     */
    public static Map<String, ReportObject> getUnresolvedReports(Map<String, ReportObject> map) {
        Map<String, ReportObject> returnedMap = new HashMap<>();
        for (Map.Entry<String, ReportObject> entry : map.entrySet()) {
            String key = entry.getKey();
            ReportObject value = entry.getValue();
            if (!entry.getValue().isResolved()){
                returnedMap.put(key, value);
            }
        }
        return returnedMap;
    }
}