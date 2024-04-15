package com.example.idlereasonsproject.FBDatabase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    public DomainNode()
    {
        getDataSnapshot();
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

    private void getDataSnapshot()
    {
        domainNode.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful())
                {
                    Log.e("firebase", "Error getting user data", task.getException());
                    //put some kind of error here
                }
                else
                {
                    Map<String, Boolean> map = new HashMap<>();

                    for(DataSnapshot child: task.getResult().getChildren())
                    {
                        map.put(child.getKey(), child.getValue(Boolean.class));
                    }

                    Log.v("firebase", "the domains are: " + map);
                    DomainNode.setDomainMap(map);
                }
            }
        });
    }

    public static void setDomainMap(Map<String, Boolean> map)
    {
        domainMap = map;
    }
}
