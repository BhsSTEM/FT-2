package com.example.idlereasonsproject;

import android.util.Log;

import com.example.idlereasonsproject.FBDatabase.Database;
import com.example.idlereasonsproject.FBDatabase.MachineNode;
import com.example.idlereasonsproject.FBDatabase.MachineObject;
import com.example.idlereasonsproject.FBDatabase.ReportNode;
import com.example.idlereasonsproject.FBDatabase.ReportObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class machineAnalysis {
//Basic machine analysis
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
    //machinesIdleReport only searches the full report list, if there's multiple reports with a machine it'll return the first
    //Might be redundant too I think Jackson added something so machineObjects have report objects attached to them
    public static ReportObject machinesIdleReport (MachineObject machine) {
        if (!isMachineIdle(machine)) {
            Log.w("machinesIdleReport", machine.getName() + "isn't idle");
            return new ReportObject("Machine isn't idle", "Machine isn't idle", "Machine isn't idle", "Machine isn't idle");
        }
        Map<String, ReportObject> unresolvedReports = reportAnalysis.getUnresolvedReports(ReportNode.getReportMap());
        for (Map.Entry<String, ReportObject> entry : unresolvedReports.entrySet()) {
            ReportObject value = entry.getValue();
            if (Objects.equals(value.getMachine(), machine.getName())) {
                return value;
            }
        }
        Log.e("machinesIdleReport", machine.getName() + " was said to be idle by isMachineIdle, but machinesIdleReport found no report");
        return new ReportObject("error", "error", "error", "error");
    }

    /**
     * Ignores inconsistent capitalization by converting to lowercase, only searches full list of machines, sorted alphabetically
     */
    public static ArrayList<String> arrayListOfTypes() {
        Map<String, MachineObject> machineMap = Database.machineNode.getMachineMap();
        ArrayList<String> returnedArrayList = new ArrayList<String>();
        for (Map.Entry<String, MachineObject> entry : machineMap.entrySet()) {
            String type = entry.getValue().getType().toLowerCase();
            if (!returnedArrayList.contains(type)) {
                returnedArrayList.add(type);
            }
        }
        returnedArrayList.sort(String::compareTo);
        return returnedArrayList;
    }
    //Uses ArrayList from arrayListOfTypes
    public static int numOfTypes() {return arrayListOfTypes().size();}
//For Filtering
    //Checks for inconsistent capitalization by converting strings to lower case
    public static Map<String, MachineObject> getMapOfMachinesOfType(String type, Map<String, MachineObject> map) {
        type = type.toLowerCase();
        Map<String, MachineObject> returnedMap = new HashMap<>();
        for (Map.Entry<String, MachineObject> entry : map.entrySet()) {
            String key = entry.getKey();
            MachineObject value = entry.getValue();
            if (type.equals(value.getType().toLowerCase())){
                returnedMap.put(key, value);
            }
        }
        return returnedMap;
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
//For sorting, the way
    /**
    *Works off arrayListOfTypes, doesn't seem to work, not sure why
     * Might be a problem with how I'm putting it into MachineListFragment
    **/
    public static ArrayList<MachineObject> sortByType(Map<String, MachineObject> map) {
        ArrayList<MachineObject> returnedArrayList = new ArrayList<>();
        ArrayList<String> types = machineAnalysis.arrayListOfTypes();
        for(int i=0; i < types.size(); i++) {
            String type = types.get(i);
            for (Map.Entry<String, MachineObject> entry : map.entrySet()) {
                MachineObject value = entry.getValue();
                if (value.getType().equals(type)){
                    returnedArrayList.add(value);
                }
            }
        }
        return returnedArrayList;
    }
//For searching
    //Ignores inconsistent capitalization by converting to lowercase, only searches name not type
    public static Map<String, MachineObject> containsSearchTerm(String searchTerm, Map<String, MachineObject> map) {
        searchTerm = searchTerm.toLowerCase();
        Map<String, MachineObject> returnedMap = new HashMap<>();
        for (Map.Entry<String, MachineObject> entry : map.entrySet()) {
            String key = entry.getKey();
            MachineObject value = entry.getValue();
            if (value.getName().contains(searchTerm)){
                returnedMap.put(key, value);
            }
        }
        return returnedMap;
    }
}
