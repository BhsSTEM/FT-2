package com.example.idlereasonsproject.FBDatabase;

public class User
{
    private String email;

    public User(){} // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User(String _email)
    {
        email = _email;
    }
}
