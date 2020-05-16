package com.slyfoxdevelopment.c196.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.slyfoxdevelopment.c196.utils.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static AppRepository ourInstance;


    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public LiveData<List< Term >> mTerms;
    public LiveData<List<Course>> mCourses;
    public LiveData<List<Assessment>> mAssessments;
    public int courseCount;



    public static AppRepository getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        mTerms = getAllTerms();
        mCourses = getAllCourses();
        mAssessments = getAllAssessments();

    }


    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {

                mDb.termDao().insertAllTerm(SampleData.getTerms());
                mDb.courseDao().insertAllCourses(SampleData.getCourses());
            }
        });
    }


    //Term Section
    private LiveData<List<Term>> getAllTerms(){
        return mDb.termDao().getAllTerms();
    }


    public void deleteAllTerms() {

        executor.execute(new Runnable() {
            @Override
            public void run() {

                mDb.termDao().deleteAll();
            }
        });
    }

    public Term getTermById(int termId) {
        return mDb.termDao().getTermById(termId);

    }


    public void insertTerm(Term term) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.termDao().insertTerm(term);
            }
        });
    }

    public void deleteTerm(final Term term) {
        executor.execute(new Runnable() {
            @Override
            public void run() {

                mDb.termDao().deleteTerm(term);
            }
        });
    }


    // Course section

    private LiveData<List<Course>> getAllCourses(){
        return mDb.courseDao().getAllCourses();
    }


    public void deleteAllCourses() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.courseDao().deleteAll();
            }
        });
    }

    public Course getCourseById(int courseId) {
        return mDb.courseDao().getCourseById(courseId);
    }

    public void insertCourse(Course course) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.courseDao().insertCourse(course);
            }
        });
    }

    public void deleteCourse(Course course) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.courseDao().deleteCourse(course);
            }
        });
    }

    //Assessments section

    private LiveData< List< Assessment>> getAllAssessments() {
        return mDb.assessmentDao().getAllAssessments();
    }


    public Assessment getAssessmentById(int assessmentId) {
        return mDb.assessmentDao().getAssessmentById(assessmentId);
    }

    public void insertAssessment(Assessment assessment) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    mDb.assessmentDao().insertAssessment(assessment);
                }
            });
    }

    public void deleteAssessment(Assessment assessment) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.assessmentDao().deleteAsseessment(assessment);
            }
        });
    }

    public void startAttachedCourseCount(int termId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
               courseCount = mDb.courseDao().getCourseCountById(termId);

            }
        });
    }

    public int getAttachedCourseCount(){

        return courseCount;
    }




}// end class
