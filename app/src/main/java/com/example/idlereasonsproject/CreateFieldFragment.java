package com.example.idlereasonsproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.idlereasonsproject.FBDatabase.Database;
import com.example.idlereasonsproject.FBDatabase.FieldNode;
import com.example.idlereasonsproject.FBDatabase.FieldObject;
import com.example.idlereasonsproject.databinding.FragmentCreateFieldBinding;
import com.example.idlereasonsproject.iface.DrawerLocker;

public class CreateFieldFragment extends Fragment
{
    private FragmentCreateFieldBinding binding;
    private FieldNode fieldNode;

    private EditText fieldNameInput;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ){
        binding = FragmentCreateFieldBinding.inflate(inflater, container, false);
        fieldNode = Database.fieldNode;
        fieldNameInput = binding.fieldNameTextInputLayout.getEditText();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceBundle)
    {
        super.onViewCreated(view, savedInstanceBundle);

        binding.createFieldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FieldObject field = new FieldObject(fieldNameInput.getText().toString());
                fieldNode.addField(new FieldObject(fieldNameInput.getText().toString()));

                NavHostFragment.findNavController(CreateFieldFragment.this)
                        .navigate(R.id.action_CreateField_to_FieldList); //later change to go to that new field details screen
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
