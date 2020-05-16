package com.slyfoxdevelopment.c196.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.slyfoxdevelopment.c196.database.AppRepository;
import com.slyfoxdevelopment.c196.database.Course;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {


    public LiveData< List< Course > >mCourses;
    private AppRepository mReposoitory;

    private int associatedId;



    public CourseViewModel(@NonNull Application application) {
        super(application);
        mReposoitory = AppRepository.getInstance(application.getApplicationContext());
        mCourses = mReposoitory.mCourses;

    }

    public void deleteAllCourses(){
        mReposoitory.deleteAllCourses();
    }


    public void setAssociateId(int termId) {
        associatedId = termId;
    }


}
