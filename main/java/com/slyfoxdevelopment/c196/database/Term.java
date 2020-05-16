package com.slyfoxdevelopment.c196.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "term_table")
public class Term {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private Date startDate;
    private Date endDate;
    private String title;

    //when we need to call an empty term obj
    @Ignore
    public Term() {
    }

    //when we want to update a term

    public Term(int id, Date startDate, Date endDate, String title) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
    }

    //when we want to create a term and assign an id automatically
    @Ignore
    public Term(Date startDate, Date endDate, String title) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Term{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", title='" + title + '\'' +
                '}';
    }
}
