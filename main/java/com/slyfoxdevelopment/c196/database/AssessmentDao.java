package com.slyfoxdevelopment.c196.database;


import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AssessmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(Assessment assessment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllAssessments(List<Assessment> assessments);

    @Delete
    void deleteAsseessment(Assessment assessment);

    @Query("SELECT * FROM assessment_table")
    LiveData<List<Assessment>> getAllAssessments();

    @Query("SELECT * FROM assessment_table WHERE id=:id")
    Assessment getAssessmentById(int id);

    @Query("DELETE FROM assessment_table")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM assessment_table")
    int getAssessmentCount();
}
