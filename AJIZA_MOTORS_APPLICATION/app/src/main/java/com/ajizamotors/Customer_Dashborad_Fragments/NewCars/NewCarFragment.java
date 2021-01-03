package com.ajizamotors.Customer_Dashborad_Fragments.NewCars;

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

public class NewCarFragment extends Fragment {

    private NewCarModel newCarModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newCarModel =
                new ViewModelProvider(this).get(NewCarModel.class);
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        newCarModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}