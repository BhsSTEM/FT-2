package com.example.idlereasonsproject.FBDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Database {
    protected final static DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private static String domain;

    public Database() { }

    protected static String getDomain()
    {
        return domain;
    }
    protected static void setDomain(String _domain)
    {
        domain = _domain;
    }
}
