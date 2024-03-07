package com.example.idlereasonsproject.FBDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database
{
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public Database(){}

    public void AddUser(String email, String firstName, String lastName, String pass)
    {
        User user = new User(email, firstName, lastName);
        Password password = new Password(pass);

        database.child("users").setValue(email);
        database.child("passwords").child(email).setValue(password);
    }
}
