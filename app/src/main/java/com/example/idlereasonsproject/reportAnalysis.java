package com.example.idlereasonsproject;

import android.util.Log;
import com.example.idlereasonsproject.FBDatabase.ReportNode;
import com.example.idlereasonsproject.FBDatabase.ReportObject;
import java.util.HashMap;
import java.util.Map;
public class reportAnalysis {
//Logging
    public static void reportObjectHashmapToLogCat(Map<String, ReportObject> map) {
        for (Map.Entry<String, ReportObject> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().reportText();
            Log.i("reportMap", "Key=" + key + ", Value=" + value);
        }
    }
    public static void reportMapReportTextToLogCat() {
        reportObjectHashmapToLogCat(ReportNode.getReportMap());
    }
//Time related functions
    public static String idleLength (long lengthValue, boolean resolvedOrNot, String machine) {
        lengthValue = lengthValue - (lengthValue % 1000);
        long numOfSeconds = lengthValue/1000;
        long numOfMinutes = (numOfSeconds - (numOfSeconds%60))/60;
        numOfSeconds = numOfSeconds%60;
        long numOfHours = (numOfMinutes - (numOfMinutes%60))/60;
        numOfMinutes = numOfMinutes%60;
        String returnedString ="";
        if (resolvedOrNot) {returnedString = machine + " was idle for ";}
        else {returnedString = machine + "has been idle for ";}
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
            if (numOfSeconds == 1) {
                returnedString = returnedString + "and 1 second.";
            }
            else {
                returnedString = returnedString + "and " + numOfSeconds + " seconds.";
            }
        }
        return returnedString;
    }
    public static long timeBetweenReportAndResolution(ReportObject report) {
        return report.getTimeOfResolution().getTime() - report.getTimeOfSubmission().getTime();
    }
//Reports from a reporter
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
