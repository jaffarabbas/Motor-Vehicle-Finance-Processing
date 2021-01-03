package com.ajizamotors.Customer_Dashborad_Fragments.UsedCars;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ajizamotors.R;

public class UsedCarFragment extends Fragment {

    private UsedCarModel usedCarModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        usedCarModel =
                new ViewModelProvider(this).get(UsedCarModel.class);
        View root = inflater.inflate(R.layout.fragment_usedcar, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        usedCarModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}