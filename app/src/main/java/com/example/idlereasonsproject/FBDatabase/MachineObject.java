package com.example.idlereasonsproject.FBDatabase;
import android.util.Log;

import com.example.idlereasonsproject.iface.DataObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MachineObject implements DataObject
{
    String name = "";
    String type = "";

    Integer val;

    String operator = "";

    String task = "";
    ArrayList<String> operators = new ArrayList<String>();

    //object to hold keys of reports
    ArrayList<String> reports = new ArrayList<>();


    //empty constructor
    public MachineObject() {}


    public MachineObject(Integer machineId, String machineName, String machineType, String machineOperator, String machineTask)
    {
        val = machineId;
        name = machineName;
        type = machineType;
        operator = machineOperator;
        task = machineTask;
    }

    public void addOperator (String addedOperator)
    {
        operators.add(addedOperator);
    }
    public void removeOperator(String removedOperator)
    {
        operators.remove(findInOperatorsArrayList(removedOperator));
    }
    public void changeType(String newType) {type = newType;}
    //private functions

    private int findInOperatorsArrayList(String searchTerm) {
        int result = -1;
        int numOfResults = 0;
        for (int i = 0; i < operators.size(); i++) {
            if (searchTerm.equals(operators.get(i))) {
                result = i;
                numOfResults += 1;
            }
        }
        if (numOfResults > 1) {
            Log.w("findInOperatorsArrayList", "Multiple results found, sending the result found last in the arraylist");
        }
        return result;
    }

    //Functions to return variables
    public String getName() {return name;}
    public String getType() {return type;}
    public ArrayList<String> getOperatorsArrayList() {return operators; }
    public ArrayList<String> getReports() { return reports; }
    public void addReport(String reportObjKey) { reports.add(reportObjKey); }

    //Map
    public Map<String, Object> toMap()
    {
        HashMap<String, Object> map  = new HashMap<>();
        map.put("name", name);
        map.put("type", type);
        map.put("operatorsArrayList", operators);
        map.put("reports", reports);
        return map;
    }

    public int getVal(){return val;}
    public String getOperator(){
        return operator;
    }
    public String getTask() {return task;}
}
