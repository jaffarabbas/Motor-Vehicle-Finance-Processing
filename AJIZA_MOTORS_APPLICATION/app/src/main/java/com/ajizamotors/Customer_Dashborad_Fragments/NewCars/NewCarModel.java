package com.ajizamotors.Customer_Dashborad_Fragments.NewCars;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewCarModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NewCarModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}