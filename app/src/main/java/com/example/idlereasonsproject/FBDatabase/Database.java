package com.example.idlereasonsproject.FBDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Database {
    protected final static DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private static String domain;
    private static User user;
    private static ReportObject currentReport;

    public static ReportNode reportNode;

    public Database() { }

    //make a set node method protected void
    protected static void setUserLoggedIn(User _user) {user = _user;}
    public static User getUserLoggedIn() { return user;}
    protected static String getDomain() {return domain;}
    protected static void setDomain(String _domain) {domain = _domain;}
    public static void setCurrentReport(ReportObject report) {currentReport = report;} //not sure why the others are protected but this needs to be called in reportidlefragment and I didn't want to risk it not working
    public static ReportObject getCurrentReport() {return currentReport;}

    public static void setNodes()
    {
        reportNode = new ReportNode();
    }
}
