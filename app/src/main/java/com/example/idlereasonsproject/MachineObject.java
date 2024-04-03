package com.example.idlereasonsproject;
import android.util.Log;

import java.util.ArrayList;

public class MachineObject {
    String name = "unknown";
    String type = "unknown";

    //variable for if there's only one operator
    String operator = "n/a";
    ArrayList<String> operatorsArrayList = new ArrayList<String>();
    int numOfOperators = 0;
    boolean moreThanOneOperator = false;

    public void addOperator (String addedOperator) {
        //add a check incase the added operator is already in the array list
        operatorsArrayList.add(addedOperator);
        numOfOperators += 1;
        switch (numOfOperators) {
            case 1:
                moreThanOneOperator = false;
                operator = addedOperator;
                break;
            case 0:
                numOfOperatorsErrorFunction();
            default:
                if (numOfOperators > 1) {
                    operator = "multiple";
                    moreThanOneOperator = true;
                    break;
                }
                else {
                    numOfOperatorsErrorFunction();
                }
        }
    }

    //private functions
    private void numOfOperatorsErrorFunction() {
        Log.e("Operator list for " + name, "Something has gone very wrong with int numOfOperators");
        numOfOperators = operatorsArrayList.size();
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
                moreThanOneOperator = true;
                operator = "multiple";
                break;
        }
    }
    private int findInOperatorsArrayList(String searchTerm) {
        int result = -1;
        int numOfResults = 0;
        for (int i = 0; i < operatorsArrayList.size(); i++) {
            result = i;
            numOfResults += 1;
        }
        if (numOfResults > 1) {
            Log.w("findInOperatorsArrayList", "Multiple results found, sending the result found last in the arraylist");
        }
        return result;
    }
}
