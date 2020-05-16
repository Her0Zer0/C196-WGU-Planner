package com.slyfoxdevelopment.c196.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessment_table")
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String title;
    private Date startDate;
    private String assessmentType;
    private String assessmentStatus;
    private int associatedId;

    @Ignore
    public Assessment() {
    }
    @Ignore
    public Assessment(@NonNull String title, Date startDate, String assessmentType, String assessmentStatus, int associatedId) {
        this.title = title;
        this.startDate = startDate;
        this.assessmentType = assessmentType;
        this.assessmentStatus = assessmentStatus;
        this.associatedId = associatedId;
    }

    public Assessment(int id, @NonNull String title, Date startDate, String assessmentType, String assessmentStatus, int associatedId) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.assessmentType = assessmentType;
        this.assessmentStatus = assessmentStatus;
        this.associatedId = associatedId;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public int getAssociatedId() {
        return associatedId;
    }

    public void setAssociatedId(int associatedId) {
        this.associatedId = associatedId;
    }

    public String getAssessmentStatus() {
        return assessmentStatus;
    }

    public void setAssessmentStatus(String assessmentStatus) {
        this.assessmentStatus = assessmentStatus;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", assessmentType='" + assessmentType + '\'' +
                ", associatedId=" + associatedId +
                '}';
    }
}
