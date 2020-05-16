package com.slyfoxdevelopment.c196.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(Course course);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllCourses(List<Course> courses);

    @Delete
    void deleteCourse(Course course);

    @Query("SELECT * FROM course_table WHERE id=:id")
    Course getCourseById(int id);

    @Query("SELECT * FROM course_table ORDER BY id ASC")
    LiveData<List<Course>> getAllCourses();

    @Query("DELETE FROM course_table")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM course_table")
    int getCourseCount();

    @Query("SELECT COUNT(*) FROM course_table WHERE associate_id=:termId")
    int getCourseCountById(int termId);
}
