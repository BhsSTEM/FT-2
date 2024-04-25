package com.example.idlereasonsproject.FBDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Database {
    protected final static DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private static String domain;
    private static User user;

    public static ReportNode reportNode;

    public static MachineNode machineNode;

    public Database() { }

    //make a set node method protected void
    protected void setUserLoggedIn(User _user)
    {
        user = _user;
    }

    public static String getDomain()
    {
        return domain;
    }
    protected static void setDomain(String _domain)
    {
        domain = _domain;
    }

    public static void setNodes()
    {
        reportNode = new ReportNode();
        machineNode = new MachineNode();
    }



}
