package com.example.idlereasonsproject.FBDatabase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MachineNode extends Database
{
    private final DatabaseReference machineNode = database.child("machines").child(getDomain()).getRef();
    private static Map<String, MachineObject> machineMap = new HashMap<>();

    public MachineNode()
    {
        //add value event listener to node
        machineNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                //update map
                Map<String, MachineObject> map = new HashMap<>();
                for(DataSnapshot child : snapshot.getChildren())
                {
                    map.put(child.getKey(), child.getValue(MachineObject.class));
                }
                Log.v("MachineNode", map.toString());
                setMachineMap(map);
                //ui changes
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Log.e("MachineNode", "failed to get reports\n" + error);
            }
        });
    }

    public void addMachine(MachineObject machine)
    {
        machineNode.child(machine.getName()).setValue(machine)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.v("MachineNode", "added machine");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("MachineNode", "can't add machine\n"+e);
                    }
                });
    }


    public void setMachineMap(Map<String, MachineObject> map){ machineMap = map; }

    public Map<String, MachineObject> getMachineMap(){ return machineMap; }
}
