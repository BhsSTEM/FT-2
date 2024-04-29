package com.example.idlereasonsproject.FBDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FieldObject
{
    private String name;

    public FieldObject(){}

    public FieldObject(String _name)
    {
        name = _name;
    }

    public String getName()
    {
        return name;
    }

    public Map<String, String> toMap()
    {
        HashMap<String, String>map = new HashMap<>();
        map.put("name", name);

        return map;
    }
}
