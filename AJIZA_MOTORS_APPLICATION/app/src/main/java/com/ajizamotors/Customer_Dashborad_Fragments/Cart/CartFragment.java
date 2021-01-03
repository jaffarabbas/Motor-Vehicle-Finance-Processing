package com.ajizamotors.Customer_Dashborad_Fragments.Cart;

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

public class CartFragment extends Fragment {

    private CartModel cartModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cartModel =
                new ViewModelProvider(this).get(CartModel.class);
        View root = inflater.inflate(R.layout.fragment_newcar, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        cartModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}