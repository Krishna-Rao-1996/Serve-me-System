package com.example.servemesystem.Homepage.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private String newText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();

    }

    public void displayNewModel(String typeName, String typeDescription){
        newText = typeName;
    }

    public LiveData<String> getText() {
        return mText;
    }
}