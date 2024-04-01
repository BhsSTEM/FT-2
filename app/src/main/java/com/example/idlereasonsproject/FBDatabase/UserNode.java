package com.example.idlereasonsproject.FBDatabase;

import android.provider.ContactsContract;
import android.util.Log;

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
    private static DataSnapshot snapshot;

    private ValueEventListener userListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot)
        {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.w("DatabaseError", "loadUser:OnCancelled", error.toException());
        }
    };

    public UserNode(){ }

    public static boolean addUser(String email, String firstName, String lastName, String pass)
    {
        email = email.toLowerCase();
        firstName = firstName.toLowerCase();
        lastName = lastName.toLowerCase();

        String key = String.valueOf(email.hashCode());

        getDataSnapshot();

        if(snapshot == null)
        {
            Log.d("firebase", "null value");
            return false; //give an error
        }

        if(snapshot.hasChild(key))
        {
            Log.w("inputTesting", "\n\n\n\nemail in use");
            return false;
        }

        DatabaseReference ref = userNode.child(key);

        User user = new User(firstName, lastName, pass);

        ref.setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void unused)
                    {
                        Log.d("firebase:setValue(user)", user.toMap().toString());
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.e("firebase:setValue(user)", "onFailure:user", e);
                    }
                }
        );

        return true;
    }

    //method used to pass snapshot into methods
    public static void setSnapshot(DataSnapshot snap)
    {
        snapshot = snap;
    }

    public static void getDataSnapshot()
    {
        userNode.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task)
            {
                if (!task.isSuccessful())
                {
                    Log.e("firebase", "Error getting user data", task.getException());
                    UserNode.setSnapshot(null);
                }
                else
                {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    UserNode.setSnapshot(task.getResult());
                }
            }
        });
    }

}
