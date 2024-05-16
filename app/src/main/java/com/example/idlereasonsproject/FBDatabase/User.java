package com.example.idlereasonsproject.FBDatabase;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String firstName;
    private String lastName;
    private String password;
    private boolean isOperator = false;
    private String email;

    public User() {} // Default constructor required for calls to DataSnapshot.getValue(User.class)

    public User(String _firstName, String _lastName, String _password, String _email) {
        firstName = _firstName;
        lastName = _lastName;
        password = _password;
        email = _email;
    }

    //store object variables in a map
    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("firstName", firstName);
        map.put("lastName", lastName);
        map.put("password", password);
        map.put("email", email);
        map.put("isOperator", isOperator);

        return map;
    }

    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String fullName() {return firstName + " " + lastName;}
    public String getPassword() {return password;}
    public boolean isOperator() {return isOperator;}
    public String getEmail() { return email; }
}
