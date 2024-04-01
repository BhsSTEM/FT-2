package com.example.idlereasonsproject.FBDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Database {
    protected final static DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public Database() { }
}
