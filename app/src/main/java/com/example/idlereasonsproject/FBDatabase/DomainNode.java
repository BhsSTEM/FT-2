package com.example.idlereasonsproject.FBDatabase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DomainNode extends Database
{
    private final static DatabaseReference domainNode = database.child("domain").getRef();
    private static Map<String, Boolean> domainMap = new HashMap<>();
    private ValueEventListener domainListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Map<String, Boolean> map = new HashMap<>();

            for(DataSnapshot child: snapshot.getChildren())
            {
                map.put(child.getKey(), child.getValue(Boolean.class));
            }

            Log.v("firebase", "the domains are: " + map);
            DomainNode.setDomainMap(map);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.e("DatabaseError", "loadUser:OnCancelled", error.toException());
        }
    };

    public DomainNode()
    {
        domainNode.addValueEventListener(domainListener);
    }

    public boolean findDomain(String domainInput)
    {
        if(domainMap.containsKey(domainInput))
        {
            setDomain(domainInput);
            return true;
        }
        return false;
    }

    public static void setDomainMap(Map<String, Boolean> map)
    {
        domainMap = map;
    }
}
