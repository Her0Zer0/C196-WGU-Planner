package com.slyfoxdevelopment.c196.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.slyfoxdevelopment.c196.database.AppRepository;
import com.slyfoxdevelopment.c196.database.Assessment;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {

    public LiveData< List< Assessment > > mAssessments;
    private AppRepository mRepository;



    public AssessmentViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mAssessments = mRepository.mAssessments;
    }


}
