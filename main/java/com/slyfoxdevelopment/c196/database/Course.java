package com.slyfoxdevelopment.c196.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "course_table")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String title;
    private Date startDate;
    private Date endDate;
    private String courseStatus;
    private String mentor;
    private String phone;
    private String email;
    private String notes;
    private int associate_id;

    //when we need to make an empty obj
    @Ignore
    public Course() {
    }
    //when we need to create a new obj
    @Ignore
    public Course(String title, Date startDate, Date endDate, String courseStatus, String mentor, String phone, String email, String notes, int associate_id) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = courseStatus;
        this.mentor = mentor;
        this.phone = phone;
        this.email = email;
        this.notes = notes;
        this.associate_id = associate_id;
    }

    //when we need to update an existing obj

    public Course(int id, String title, Date startDate, Date endDate, String courseStatus, String mentor, String phone, String email, String notes, int associate_id) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = courseStatus;
        this.mentor = mentor;
        this.phone = phone;
        this.email = email;
        this.notes = notes;
        this.associate_id = associate_id;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getAssociate_id() {
        return associate_id;
    }

    public void setAssociate_id(int associate_id) {
        this.associate_id = associate_id;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", courseStatus='" + courseStatus + '\'' +
                ", mentor='" + mentor + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", notes='" + notes + '\'' +
                ", associate_id=" + associate_id +
                '}';
    }
}
