package com.example.idlereasonsproject.FBDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database
{
    private static DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public Database(){}

    public static void AddUser(String email, String firstName, String lastName, String pass)
    {
        User user = new User(email, firstName, lastName, pass);

        database.child("users").child(email).setValue(user);
    }
}
