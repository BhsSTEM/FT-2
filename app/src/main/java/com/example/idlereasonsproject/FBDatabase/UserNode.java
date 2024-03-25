package com.example.idlereasonsproject.FBDatabase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class UserNode extends Database
{
    private final static DatabaseReference userNode = database.child("users").getRef();

    public UserNode(){ }

    public static boolean addUser(String email, String firstName, String lastName, String pass)
    {
        email = email.toLowerCase();
        firstName = firstName.toLowerCase();
        lastName = lastName.toLowerCase();

        String key = String.valueOf(email.hashCode());

        /*
        try
        {
            if (getDataSnapshot().hasChild(key)) {
                return false;
            }
        }
        catch(Exception e)
        {
            new Error(e).printStackTrace();
        }

        if (getDataSnapshot().hasChild(key)) {
            return false;
        }*/


        DatabaseReference ref = userNode.child(key);

        User user = new User(firstName, lastName, pass);

        ref.setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void unused)
                    {
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        new Error(e).printStackTrace(); //error if there is a problem
                    }
                }
        );

        return true;
    }

    public static DataSnapshot getDataSnapshot()
    {
        return userNode.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task)
            {
                if (!task.isSuccessful())
                {
                    new Error(task.getException()).printStackTrace();
                }

            }
        }).getResult();
    }
}
