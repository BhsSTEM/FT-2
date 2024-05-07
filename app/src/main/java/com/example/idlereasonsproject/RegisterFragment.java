package com.example.idlereasonsproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.idlereasonsproject.FBDatabase.Database;
import com.example.idlereasonsproject.FBDatabase.UserNode;
import com.example.idlereasonsproject.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    private FragmentSecondBinding binding;
    private EditText firstNameInput;
    private EditText lastNameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText cfnPasswordInput;
    private UserNode userNode = new UserNode();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        firstNameInput = binding.firstNameInput.getEditText();
        lastNameInput = binding.lastNameInput.getEditText();
        emailInput = binding.emailInput.getEditText();
        passwordInput = binding.passwordInput.getEditText();
        cfnPasswordInput = binding.confirmPwdInput.getEditText();

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //login button actions
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("btnTest", "login click");
                NavHostFragment.findNavController(RegisterFragment.this)
                        .navigate(R.id.action_RegisterFragment_to_LoginFragment);
            }
        });

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            String email;
            String firstName;
            String lastName;
            String password;
            String cfnPassword;

            @Override
            public void onClick (View v)
            {
                Log.v("btnTest", "click");
                email = emailInput.getText().toString();
                firstName = firstNameInput.getText().toString();
                lastName = lastNameInput.getText().toString();
                password = passwordInput.getText().toString();
                cfnPassword = cfnPasswordInput.getText().toString();

                if (checkUserInputs()) {
                    boolean userMade = userNode.addUser(
                            email,
                            firstName,
                            lastName,
                            cfnPassword
                    );

                    if (userMade) {
                        emailInput.setError("EMAIL ALREADY IN USE");
                    } else {
                        Database.setNodes();
                        NavHostFragment.findNavController(RegisterFragment.this)
                                .navigate(R.id.action_RegisterFragment_to_HomeFragment);
                    }
                }
            }

            //method to check if any user inputs are empty
            private boolean checkUserInputs ()
            {
                boolean acceptableInputs = true;

                if (firstName.equals("")) {
                    firstNameInput.setError("PLEASE ENTER FIRST NAME >:(");
                    acceptableInputs = false;
                }

                if (lastName.equals("")) {
                    lastNameInput.setError("PLEASE ENTER LAST NAME >:(");
                    acceptableInputs = false;
                }

                if (email.equals("")) {
                    emailInput.setError("PLEASE ENTER EMAIL >:(");
                    acceptableInputs = false;
                }

                if (password.equals("")) {
                    passwordInput.setError("PLEASE ENTER PASSWORD >:(");
                    acceptableInputs = false;
                }
                else if (!meetsPasswordRequirements(password).equals("Meets requirements")) {
                    passwordInput.setError(meetsPasswordRequirements(password));
                    acceptableInputs = false;
                }

                if (cfnPassword.equals("")) {
                    cfnPasswordInput.setError("PLEASE ENTER PASSWORD >:(");
                    acceptableInputs = false;
                }
                else if (!cfnPassword.equals(passwordInput.getText().toString())) {
                    cfnPasswordInput.setError("PASSWORDS DON'T MATCH >:(");
                    acceptableInputs = false;
                }

                return acceptableInputs;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
    //Register button actions



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public String meetsPasswordRequirements(String password) {
        String returnedString ="";
        boolean hasCapital = false;
        boolean hasLowercase = false;
        boolean hasNumberOrSpecialCharacter = false;
        for (int i = 0; i < password.length(); i++) {
            char character = password.charAt(i);
            if (Character.isUpperCase(character)) {hasCapital = true;}
            if (Character.isLowerCase(character)) {hasLowercase = true;}
            if (!Character.isAlphabetic(character)) {hasNumberOrSpecialCharacter = true;}
            if (hasCapital && hasLowercase && hasNumberOrSpecialCharacter) {return "Meets requirements";}
        }
        returnedString = "Missing requirement(s): ";
        if (!hasCapital) { returnedString = returnedString + "Needs capital letter ";}
        if (!hasLowercase) { returnedString = returnedString + "Needs lowercase letter ";}
        if (!hasNumberOrSpecialCharacter) { returnedString = returnedString + "Needs number/special character ";}
        return returnedString;
    }

}