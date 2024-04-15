package com.example.idlereasonsproject.FBDatabase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Repo;

import java.util.HashMap;
import java.util.Map;

public class ReportNode extends Database
{
    private DatabaseReference reportNode = database.child("reports").child(getDomain()).getRef();
    private static Map<String, ReportObject> reportMap = new HashMap<>();

    public ReportNode()
    {
        //add value event listener to node
        reportNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                //update map
                Map<String, ReportObject> map = new HashMap<>();
                for(DataSnapshot child : snapshot.getChildren())
                {
                    reportMap.put(child.getKey(), child.getValue(ReportObject.class));
                }

                setReportMap(map);
                //notification
                //ui changes
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Log.e("ReportNode", "failed to get reports\n" + error);
            }
        });
    }

    public void addReportToDB(ReportObject report)
    {
        reportNode.child(String.valueOf(report.getTimeOfSubmission().getTime())).setValue(report)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.v("ReportNode", "added report");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("ReportNode", "can't add report\n"+e);
                    }
                });
    }

    public void setReportMap(Map<String, ReportObject> map)
    {
        reportMap = map;
    }

    public Map getReportMap()
    {
        return reportMap;
    }

}
