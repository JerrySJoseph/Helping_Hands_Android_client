package com.jstechnologies.helpinghands.ui.views.base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import android.icu.util.ValueIterator;
import android.os.Bundle;

import com.jstechnologies.helpinghands.R;

public abstract class BaseActivity <VM extends ViewModel> extends AppCompatActivity {

    protected VM viewmodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        viewmodel=createViewModel();
        observe();
    }
    @NonNull
    protected abstract VM createViewModel();

    protected abstract void onMessage(String message);

    protected abstract void observe();

}