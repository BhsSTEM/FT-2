package com.example.idlereasonsproject.FBDatabase;

import com.google.firebase.database.DatabaseReference;

public class UserNode extends Database
{
    private final static DatabaseReference userNode = database.child("users").getRef();

    public UserNode(){ }

    public static void addUser(String email, String firstName, String lastName, String pass)
    {
        DatabaseReference ref = userNode.child(email);

        User user = new User(firstName, lastName, pass);

        ref.setValue(user);
    }

    public static void getDataSnapshot()
    {
        //userNode.get().addOnCompleteListener();
    }
}
