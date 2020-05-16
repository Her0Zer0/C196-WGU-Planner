package com.slyfoxdevelopment.c196.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.slyfoxdevelopment.c196.utils.Util;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 6, exportSchema = false)
@TypeConverters(Util.class)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "APPDATABASE.db";

    public static volatile AppDatabase instance;

    private static final Object LOCK = new Object();

    public abstract TermDao termDao();

    public abstract CourseDao courseDao();

    public abstract AssessmentDao assessmentDao();

    public static AppDatabase getInstance(Context context) {
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }










}//END CLASS
