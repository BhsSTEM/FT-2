package com.example.idlereasonsproject;

import android.util.Log;

import com.example.idlereasonsproject.FBDatabase.ReportNode;
import com.example.idlereasonsproject.FBDatabase.ReportObject;
import com.google.firebase.database.core.Repo;

import java.util.HashMap;
import java.util.Map;

public class reportAnalysis {
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
    public static Map<String, ReportObject> unresolvedReports(Map<String, ReportObject> map) {
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
    public long timeBetweenReportAndResolution(ReportObject report) {return report.getTimeOfResolution().getTime() - report.getTimeOfSubmission().getTime();}
}
