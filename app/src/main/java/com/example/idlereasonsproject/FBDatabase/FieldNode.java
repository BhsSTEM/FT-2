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

public class FieldNode extends Database
{
    private final DatabaseReference fieldNode = database.child("fields").child(getDomain()).getRef();
    private static Map<String, FieldObject> fieldMap = new HashMap<>();

    public FieldNode()
    {
        fieldNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                fieldMap.clear();
                Map<String, FieldObject> map = new HashMap<>();

                for(DataSnapshot child: snapshot.getChildren())
                {
                    map.put(child.getKey(), child.getValue(FieldObject.class));
                }

                Log.v("FieldNode", map.toString());
                setFieldMap(map);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FieldNode", "failed to get fields\n"+error);
            }
        });
    }

    public void addField(FieldObject field)
    {
        fieldNode.child(field.getName()).setValue(field)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.v("FieldNode", "added fields");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FieldNode", "can't add field\n"+e);
                    }
                });;
    }

    public void setFieldMap(Map<String, FieldObject> map) { fieldMap = map; }
    public Map<String, FieldObject> getFieldMap(){ return fieldMap; }
}
