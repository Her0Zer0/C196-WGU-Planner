package com.slyfoxdevelopment.c196.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.slyfoxdevelopment.c196.database.AppRepository;
import com.slyfoxdevelopment.c196.database.Course;
import com.slyfoxdevelopment.c196.utils.Util;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CourseEditViewModel extends AndroidViewModel {

    private static final String TAG = "CourseEditViewModel";
    public MutableLiveData< Course > mLiveCourse = new MutableLiveData<>();

    private AppRepository mRepository;

    private Executor executor = Executors.newSingleThreadExecutor();

    public CourseEditViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadCourseData(int courseId){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Course course = mRepository.getCourseById(courseId);
                mLiveCourse.postValue(course);
            }
        });
    }

    public void saveCourse(String courseTitle, String courseStartDate, String courseEndDate, String courseStatus, String mentor, String phone, String email, String notes, int associatedId) {
        Course course = mLiveCourse.getValue();
        //new submission
        if(course == null){

            course = new Course(courseTitle,
                    Util.convertToDate(courseStartDate),
                    Util.convertToDate(courseEndDate),
                    courseStatus,
                    mentor,
                    phone,
                    email,
                    notes,
                    associatedId);
        }else{//updating a course
            course.setTitle(courseTitle);
            course.setStartDate(Util.convertToDate(courseStartDate));
            course.setEndDate(Util.convertToDate(courseEndDate));
            course.setCourseStatus(courseStatus);
            course.setMentor(mentor);
            course.setPhone(phone);
            course.setEmail(email);
            course.setNotes(notes);
            course.setAssociate_id(associatedId);
        }

        mRepository.insertCourse(course);


    }

    public void deleteCourse() {
        mRepository.deleteCourse(mLiveCourse.getValue());
    }
}
