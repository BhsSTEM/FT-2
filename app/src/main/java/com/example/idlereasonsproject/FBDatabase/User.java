package com.example.idlereasonsproject.FBDatabase;

import java.util.HashMap;
import java.util.Map;

public class User
{
    private String firstName;
    private String lastName;
    private String password;

    public User(){} // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User(String _firstName, String _lastName, String _password)
    {
        firstName = _firstName;
        lastName = _lastName;
        password = _password;
    }

    public Map<String, Object> toMap()
    {
        HashMap<String, Object> map  = new HashMap<>();
        map.put("firstName", firstName);
        map.put("lastName", lastName);
        map.put("password", password);

        return map;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastname()
    {
        return lastName;
    }

    public String getPassword()
    {
        return password;
    }
}
