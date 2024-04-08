package com.example.idlereasonsproject;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MachineObject {
    String name = "unknown";
    String type = "unknown";

    //variable for if there's only one operator
    String operator = "n/a";
    ArrayList<String> operatorsArrayList = new ArrayList<String>();
    int numOfOperators = 0;
    boolean moreThanOneOperator = false;

    //empty constructor
    public MachineObject() {}
    //constructor with name and type
    public MachineObject(String machineName, String machineType) {
        name = machineName;
        type = machineType;
    }

    public void addOperator (String addedOperator) {
        //add a check incase the added operator is already in the array list
        operatorsArrayList.add(addedOperator);
        numOfOperators += 1;
        moreThanOneOperatorSwitch();
    }
    public void removeOperator(String removedOperator) {
        removeDuplicatesInOperatorsArrayList(removedOperator);
        operatorsArrayList.remove(findInOperatorsArrayList(removedOperator));
        numOfOperators -= 1;
        moreThanOneOperatorSwitch();
    }
    public void changeType(String newType) {type = newType;}
    //private functions
    private void moreThanOneOperatorSwitch() {
        switch (numOfOperators) {
            case 1:
                moreThanOneOperator = false;
                operator = operatorsArrayList.get(0);
                break;
            case 0:
                moreThanOneOperator = false;
                operator = "n/a";
                break;
            default:
                if (numOfOperators > 1) {
                    moreThanOneOperator = true;
                    operator = "multiple";
                    break;
                }
                else {
                    numOfOperatorsErrorFunction();
                    break;
                }
        }
    }
    private void numOfOperatorsErrorFunction() {
        Log.e("Operator list for " + name, "Something has gone very wrong with int numOfOperators");
        numOfOperators = operatorsArrayList.size();
        moreThanOneOperatorSwitch();
    }
    private int findInOperatorsArrayList(String searchTerm) {
        int result = -1;
        int numOfResults = 0;
        for (int i = 0; i < operatorsArrayList.size(); i++) {
            if (searchTerm.equals(operatorsArrayList.get(i))) {
                result = i;
                numOfResults += 1;
            }
        }
        if (numOfResults > 1) {
            Log.w("findInOperatorsArrayList", "Multiple results found, sending the result found last in the arraylist");
        }
        return result;
    }
    private void removeDuplicatesInOperatorsArrayList(String searchTerm) {
        int searchTermIndex = findInOperatorsArrayList(searchTerm);
        if (searchTermIndex != -1) {
            for (int i = 0; i < operatorsArrayList.size(); i++) {
                if (searchTerm.equals(operatorsArrayList.get(i)) && searchTermIndex != i) {
                    operatorsArrayList.remove(i);
                    numOfOperators -= 1;
                    moreThanOneOperatorSwitch();
                }
            }
        }
        else {
            Log.i("Operator ArrayList duplicate removal", "search term not in arraylist");
        }
    }

    //Functions to return variables
    public String getName() {return name;}
    public String getType() {return type;}
    public String getOperator() {return operator;}
    public ArrayList<String> getOperatorsArrayList() {return operatorsArrayList;}
    public int getNumOfOperators() {return numOfOperators;}
    public boolean isMoreThanOneOperator() {return moreThanOneOperator;}

    //Map
    public Map<String, Object> toMap()
    {
        HashMap<String, Object> map  = new HashMap<>();
        map.put("name", name);
        map.put("type", type);
        map.put("operator", operator);
        map.put("operatorsArrayList", operatorsArrayList);
        map.put("numOfOperators", numOfOperators);
        map.put("moreThanOneOperator", moreThanOneOperator);
        return map;
    }
}
