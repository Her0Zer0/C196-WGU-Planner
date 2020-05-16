package com.slyfoxdevelopment.c196.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TermDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTerm(Term term);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllTerm(List<Term> term);

    @Delete
    void deleteTerm(Term term);

    @Query("SELECT * FROM term_table WHERE id=:id")
    Term getTermById(int id);

    @Query("SELECT * FROM term_table ORDER BY id ASC")
    LiveData<List<Term>> getAllTerms();

    @Query("DELETE FROM term_table")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM term_table")
    int getTermCount();
}
