package com.example.idlereasonsproject;

import com.example.idlereasonsproject.FBDatabase.MachineObject;
import com.example.idlereasonsproject.FBDatabase.ReportNode;
import com.example.idlereasonsproject.FBDatabase.ReportObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class machineAnalysis {
//For Filtering
    //No checks for inconsistent capitalization
    public static Map<String, MachineObject> getMapOfMachinesOfType(String type, Map<String, MachineObject> map) {
        Map<String, MachineObject> returnedMap = new HashMap<>();
        for (Map.Entry<String, MachineObject> entry : map.entrySet()) {
            String key = entry.getKey();
            MachineObject value = entry.getValue();
            if (type.equals(value.getType())){
                returnedMap.put(key, value);
            }
        }
        return returnedMap;
    }
    //isMachineIdle only searches the full report list, and also rely on getMachine returning a string and not a machine object
    public static boolean isMachineIdle (MachineObject machine) {
        Map<String, ReportObject> unresolvedReports = reportAnalysis.getUnresolvedReports(ReportNode.getReportMap());
        for (Map.Entry<String, ReportObject> entry : unresolvedReports.entrySet()) {
            ReportObject value = entry.getValue();
            if (Objects.equals(value.getMachine(), machine.getName())) {
                return true;
            }
        }
        return false;
    }
    //Uses isMachineIdle and therefore works with the full report list
    public static Map<String, MachineObject> getMapOfIdleMachines(Map<String, MachineObject> map) {
        Map<String, MachineObject> returnedMap = new HashMap<>();
        for (Map.Entry<String, MachineObject> entry : map.entrySet()) {
            String key = entry.getKey();
            MachineObject value = entry.getValue();
            if (isMachineIdle(value)){
                returnedMap.put(key, value);
            }
        }
        return returnedMap;
    }
    public static Map<String, MachineObject> getMapOfNotIdleMachines(Map<String, MachineObject> map) {
        Map<String, MachineObject> returnedMap = new HashMap<>();
        for (Map.Entry<String, MachineObject> entry : map.entrySet()) {
            String key = entry.getKey();
            MachineObject value = entry.getValue();
            if (!isMachineIdle(value)){
                returnedMap.put(key, value);
            }
        }
        return returnedMap;
    }
//For sorting (if i can think of anything)
}
