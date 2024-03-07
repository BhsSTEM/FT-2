package com.example.idlereasonsproject.FBDatabase;

public class User
{
    private String email;
    private String firstName;
    private String lastName;

    public User(){} // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User(String _email, String _firstName, String _lastName)
    {
        email = _email;
        firstName = _firstName;
        lastName = _lastName;
    }
}
