package com.ajizamotors.Customer_Dashborad_Fragments.UsedCars;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UsedCarModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UsedCarModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}