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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserNode extends Database
{
    private final static DatabaseReference userNode = database.child("users").getRef();
    private static Map<String, User> usersHashMap = new HashMap<>();

    private ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                Map<String, User> userMap = new HashMap<>();

                for (DataSnapshot child : snapshot.getChildren())
                {
                    userMap.put(child.getKey(), child.getValue(User.class));
                }

                Log.v("firebase", "the users are: " + userMap);
                UserNode.setUsersHashMap(userMap);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Log.w("DatabaseError", "loadUser:OnCancelled", error.toException());
            }
    };

    public UserNode()
    {
        userNode.addValueEventListener(userListener);
    }

    public boolean loginUser(String email, String password)
    {
        String key = String.valueOf(email.hashCode());

        if(!usersHashMap.containsKey(key))
        {
            Log.i("userInputLogin", "email or password are incorrect");
            return false;
        }

        String pass = Objects.requireNonNull(usersHashMap.get(key)).getPassword();

        if(!pass.equals(password))
        {
            Log.i("userInputLogin", "email or password are incorrect");
            return false;
        }

        return true;
    }

    public boolean addUser(String email, String firstName, String lastName, String pass)
    {
        email = email.toLowerCase();
        firstName = firstName.toLowerCase();
        lastName = lastName.toLowerCase();

        String key = String.valueOf(email.hashCode());

        if(usersHashMap.containsKey(key))
        {
            Log.i("userInputRegister", "email exists!!!");
            return true;
        }

        DatabaseReference ref = userNode.child(key);

        User user = new User(firstName, lastName, pass);

        ref.setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void unused)
                    {
                        Log.v("setUser", user.toMap().toString());
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

        return false;
    }

    //method used to pass snapshot into methods
    public static void setUsersHashMap(Map<String, User> map)
    {
        usersHashMap = map;
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
                }
                else
                {
                    Log.d("firebase", "read success " + String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }

}
