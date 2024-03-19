package com.example.idlereasonsproject.FBDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Database
{
    protected final static DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public Database(){}

    public static void addUser(String email, String firstName, String lastName, String pass)
    {
        DatabaseReference ref = database.child("users").child(email);
        User user = new User(firstName, lastName, pass);

        ref.setValue(user);
    }

}
