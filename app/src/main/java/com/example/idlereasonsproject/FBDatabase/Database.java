package com.example.idlereasonsproject.FBDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database
{
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public Database(){}

    public void AddUser(String email)
    {
        User user = new User(email);

        database.child("users").setValue(email);
    }
}
