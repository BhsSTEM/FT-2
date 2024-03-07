package com.example.idlereasonsproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.idlereasonsproject.databinding.FragmentSecondBinding;
import com.example.idlereasonsproject.FBDatabase.Database;
import com.google.android.material.textfield.TextInputLayout;

public class SecondFragment extends Fragment
{

    private FragmentSecondBinding binding;

    private EditText firstNameInput;
    private EditText lastNameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText cfnPasswordInput;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);

        firstNameInput = binding.firstNameInput.getEditText();
        lastNameInput = binding.lastNameInput.getEditText();
        emailInput = binding.emailInput.getEditText();
        passwordInput = binding.passwordInput.getEditText();
        cfnPasswordInput = binding.confirmPwdInput.getEditText();

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        binding.loginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        binding.registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if(checkUserInputs())
                {
                    //add new user
                    //switch to screen
                }
            }

            //method to check if any user inputs are empty
            private boolean checkUserInputs()
            {
                boolean acceptableInputs = true;

                if(firstNameInput.getText().toString().equals(""))
                {
                    firstNameInput.setError("PLEASE ENTER FIRST NAME >:(");
                    acceptableInputs = false;
                }

                if(lastNameInput.getText().toString().equals(""))
                {
                    lastNameInput.setError("PLEASE ENTER LAST NAME >:(");
                    acceptableInputs = false;
                }

                if(emailInput.getText().toString().equals(""))
                {
                    emailInput.setError("PLEASE ENTER EMAIL >:(");
                    acceptableInputs = false;
                }

                if(passwordInput.getText().toString().equals(""))
                {
                    passwordInput.setError("PLEASE ENTER PASSWORD >:(");
                    acceptableInputs = false;
                }

                if(cfnPasswordInput.getText().toString().equals(passwordInput.getText().toString()))
                {
                    cfnPasswordInput.setError("PASSWORDS DON'T MATCH >:(");
                    acceptableInputs = false;
                }

                return acceptableInputs;
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}