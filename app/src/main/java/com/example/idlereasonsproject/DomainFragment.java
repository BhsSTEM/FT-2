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

import com.example.idlereasonsproject.FBDatabase.DomainNode;
import com.example.idlereasonsproject.databinding.FragmentDomainBinding;

public class DomainFragment extends Fragment
{
    private FragmentDomainBinding binding;
    private EditText domainInput;
    private final DomainNode domainNode = new DomainNode();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    )
    {
        binding = FragmentDomainBinding.inflate(inflater, container, false);
        domainInput = binding.domainTextEdit.getEditText();

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        //put binding check here
        binding.domainEnterBtn.setOnClickListener(new View.OnClickListener() {
            String domain;

            @Override
            public void onClick(View v)
            {
                domain = domainInput.getText().toString();
                boolean domainExists = domainNode.findDomain(domain);

                if(domainExists)
                {
                    NavHostFragment.findNavController(DomainFragment.this)
                            .navigate(R.id.action_DomainFragment_to_FirstFragment);
                }
                else
                {
                    domainInput.setError("Domain doesn't exist");
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
