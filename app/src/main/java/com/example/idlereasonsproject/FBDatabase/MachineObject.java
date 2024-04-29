package com.example.idlereasonsproject.FBDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MachineObject {
    String name = "";
    String type = "";

    String id ="";

    Integer val;
    ArrayList<String> operators = new ArrayList<String>();


    //empty constructor
    public MachineObject() {}


    public MachineObject(String machineId, String machineName, String machineType)
    {
        id = machineId;
        name = machineName;
        type = machineType;
    }

    //constructor with name and type
    public MachineObject(String machineName, String machineType)
    {
        name = machineName;
        type = machineType;
    }



    public MachineObject(String machineName, String machineType, ArrayList<String> operatorsList)
    {
        name = machineName;
        type = machineType;
        operators = operatorsList;
    }


    public MachineObject(String machineId, String machineName, String machineType, ArrayList<String> operatorsList)
    {
        id = machineId;
        name = machineName;
        type = machineType;
        operators = operatorsList;
    }

    public MachineObject(Integer machineId, String machineName, String machineType, ArrayList<String> operatorsList)
    {
        val = machineId;
        name = machineName;
        type = machineType;
        operators = operatorsList;
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

    //Map
    public Map<String, Object> toMap()
    {
        HashMap<String, Object> map  = new HashMap<>();
        map.put("name", name);
        map.put("type", type);
        map.put("operatorsArrayList", operators);
        return map;
    }

    public String getId() {
        return id;
    }

    public int getVal(){
        return val;
    }
}
