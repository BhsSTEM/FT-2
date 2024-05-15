package com.example.idlereasonsproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.idlereasonsproject.FBDatabase.Database;
import com.example.idlereasonsproject.FBDatabase.UserNode;

import com.example.idlereasonsproject.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private EditText emailInput;
    private EditText passwordInput;
    private UserNode userNode = new UserNode();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        //((MainActivity) requireActivity()).getSupportActionBar().hide();

        emailInput = binding.emailTextInputLayout.getEditText();
        passwordInput = binding.passwordTextInputLayout.getEditText();

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_LoginFragment_to_RegisterFragment);
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener(){
            String email;
            String password;

            @Override
            public void onClick(View v)
            {
                email = emailInput.getText().toString();
                password = passwordInput.getText().toString();

                boolean loginStatus = userNode.loginUser(email, password);

                if(loginStatus)
                {
                    Log.i("login","complete");
                    Database.setNodes();
                    NavHostFragment.findNavController(LoginFragment.this)
                            .navigate(R.id.action_LoginFragment_to_HomeFragment);
                }
                else
                {
                    emailInput.setError("email or password wrong");
                }
            }
        });


    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}