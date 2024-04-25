package com.example.idlereasonsproject;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.idlereasonsproject.FBDatabase.Database;
import com.example.idlereasonsproject.FBDatabase.ReportNode;
import com.example.idlereasonsproject.FBDatabase.ReportObject;
import com.example.idlereasonsproject.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        Log.i("loginStatus", "success :)");
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.trackerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_HomeFragment_to_TrackerFragment);
            }
        });

        binding.machineListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_HomeFragment_to_MachineListFragment);
            }
        });

        binding.idleReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //switch blocks users from accessing page if they have unresolved reports
               switch (reportAnalysis.numOfUnresolvedReports(reportAnalysis.reportsFromReporter(Database.getUserLoggedIn().fullName(), ReportNode.getReportMap()))) {
                   case 0:
                       Log.i("Report page block", "case 0");
                       NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_HomeFragment_to_ReportIdle);
                       break;
                   case 1:
                       Log.i("Report page block", "case 1");
                       Toast.makeText(getActivity(),"You already have a report active, please resolve that before making another report",Toast.LENGTH_LONG).show();
                       break;
                   default:
                       Log.i("Report page block", "default");
                       Log.e("Reports in database", "More than 1 unresolved report (or maybe less than 0 somehow) from user " + Database.getUserLoggedIn().fullName());
                       Toast.makeText(getActivity(),"You have more than one report active, this is likely the result of some error",Toast.LENGTH_LONG).show();
                       break;

               }
            }
        });

        binding.resolveReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportObject resolvedReport = Database.getCurrentReport();
                if (resolvedReport != null) {
                    //two seperate if statements to prevent the chance of a null pointer function
                    if (!resolvedReport.isResolved()) {
                        Database.setCurrentReport(ReportNode.resolveReport(resolvedReport));
                    }
                    else {
                        Log.i("Report Resolution", "Report already resolved");
                    }
                }
                else {
                    Log.i("Report Resolution", "No report currently");
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}