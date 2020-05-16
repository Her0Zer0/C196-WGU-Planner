package com.slyfoxdevelopment.c196.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.slyfoxdevelopment.c196.database.AppRepository;
import com.slyfoxdevelopment.c196.database.Assessment;
import com.slyfoxdevelopment.c196.utils.Util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AssessmentEditViewModel extends AndroidViewModel {

    private static final String TAG = "AssessmentEditViewModel";

    public MutableLiveData< Assessment > mLiveAssessments = new MutableLiveData<>();

    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public AssessmentEditViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(application.getApplicationContext());
    }

    public void loadAssessmentContent(int assessmentId){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Assessment assessment = mRepository.getAssessmentById(assessmentId);
                mLiveAssessments.postValue(assessment);
            }
        });
    }


    public void saveAssessment(String title, String startDate, String assetType, String assetStatus, int associatedCourseId) {

        Assessment assessment = mLiveAssessments.getValue();

        if(assessment == null){
            assessment = new Assessment(
                    title,
                    Util.convertToDate(startDate),
                    assetType,
                    assetStatus,
                    associatedCourseId
            );
        }else{
            assessment.setTitle(title);
            assessment.setStartDate(Util.convertToDate(startDate));
            assessment.setAssessmentType(assetType);
            assessment.setAssessmentStatus(assetStatus);
            assessment.setAssociatedId(associatedCourseId);
        }


        mRepository.insertAssessment(assessment);



    }

    public void deleteAssessment() {

        mRepository.deleteAssessment(mLiveAssessments.getValue());
    }
}
