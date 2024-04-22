package com.example.idlereasonsproject;

import android.util.Log;

import com.example.idlereasonsproject.FBDatabase.ReportNode;
import com.example.idlereasonsproject.FBDatabase.ReportObject;

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
    public static int numOfReportsFromReporter(String reporter) {
        int numOfReports = 0;
        for (Map.Entry<String, ReportObject> entry : ReportNode.getReportMap().entrySet()) {
            String valueOfReporter = entry.getValue().getReporter();
            if (reporter.equals(valueOfReporter)){
                numOfReports++;
            }
        }
        return numOfReports;
    }
    public static Map<String, ReportObject> reportsFromReporter(String reporter) {
        Map<String, ReportObject> returnedMap = new HashMap<>();
        for (Map.Entry<String, ReportObject> entry : ReportNode.getReportMap().entrySet()) {
            String key = entry.getKey();
            ReportObject value = entry.getValue();
            if (reporter.equals(value.getReporter())){
                returnedMap.put(key, value);
            }
        }
        return returnedMap;
    }
}
