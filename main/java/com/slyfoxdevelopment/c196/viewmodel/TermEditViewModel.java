package com.slyfoxdevelopment.c196.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.slyfoxdevelopment.c196.database.AppRepository;
import com.slyfoxdevelopment.c196.database.Term;
import com.slyfoxdevelopment.c196.utils.Util;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TermEditViewModel extends AndroidViewModel {

    public MutableLiveData< Term > mLiveTerm = new MutableLiveData<>();


    private AppRepository mRepository;

    private Executor executor = Executors.newSingleThreadExecutor();

    public TermEditViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }


    public void loadTermData(int termId) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Term term = mRepository.getTermById(termId);
                mLiveTerm.postValue(term);
            }
        });
    }

    public void saveTerm(String termStartDate, String termEndDate, String termTitle) {
        Term term = mLiveTerm.getValue();

        Date convertedStartDate = Util.convertToDate(termStartDate.toString());
        Date convertedEndDate = Util.convertToDate(termEndDate.toString());

//        Log.i("TermEditviewModel", convertedStartDate.toString() + " " + convertedEndDate.toString());
        if(term == null){
            if( TextUtils.isEmpty(termTitle.trim()) || TextUtils.isEmpty(termStartDate) || TextUtils.isEmpty(termEndDate)){
                return;
            }

            term = new Term(Util.convertToDate(termStartDate), Util.convertToDate(termEndDate), termTitle);

        }else{
//            term.setId(term.getId());
            term.setTitle(termTitle.trim());
            term.setStartDate(Util.convertToDate(termStartDate));
            term.setEndDate(Util.convertToDate(termEndDate));
        }

        mRepository.insertTerm(term);


    }

    public void deleteTerm() {
        mRepository.deleteTerm(mLiveTerm.getValue());
    }

    public int getAttachedCourseCount() {
        return mRepository.getAttachedCourseCount();
    }

    public void startAttachedCourseCount(int termId) {
        mRepository.startAttachedCourseCount(termId);
    }
}
