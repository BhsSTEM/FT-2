package com.example.idlereasonsproject.FBDatabase;

public class User
{
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    public User(){} // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User(String _email, String _firstName, String _lastName, String _password)
    {
        email = _email;
        firstName = _firstName;
        lastName = _lastName;
        password = _password;
    }
}
