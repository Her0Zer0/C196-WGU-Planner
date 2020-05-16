package com.slyfoxdevelopment.c196.utils;

import com.slyfoxdevelopment.c196.database.Course;
import com.slyfoxdevelopment.c196.database.Term;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleData {


    public static final String SAMPLE_TERM_1 = "Term 1";
    public static final String SAMPLE_TERM_2 = "Term 2";
    public static final String SAMPLE_TERM_3 = "Term 3";

    public static final String SAMPLE_COURSE_1 = "Under Water Basket Weaving";
    public static final String SAMPLE_COURSE_2 = "Arm Wrestling";
    public static final String SAMPLE_COURSE_3 = "Surfing";




    private static Date getDate(int diff){
        GregorianCalendar cal = new GregorianCalendar();

        cal.add(Calendar.MONTH, diff);
        return cal.getTime();
    }


    public static List< Term > getTerms(){
        List<Term> termsList = new ArrayList<>();

        termsList.add(new Term(1, getDate(0), getDate(5), SAMPLE_TERM_1));
        termsList.add(new Term(2, getDate(5), getDate(10), SAMPLE_TERM_2));
        termsList.add(new Term(3, getDate(11), getDate(16), SAMPLE_TERM_3));

        return termsList;
    }


//    Course(int id, String title, Date startDate, Date endDate, String courseStatus, String mentor, String phone, String email, String notes, int associate_id)

    public static List< Course > getCourses(){
        List<Course> courseList = new ArrayList<>();

        courseList.add(new Course(SAMPLE_COURSE_1, getDate(0), getDate(1), "Not Scheduled", "Dr. Robotnick", "123-456-7896", "iluvsonic@evil.com", "To begin this course, pick up the sega controller and press start", 1));
        courseList.add(new Course(SAMPLE_COURSE_2, getDate(1), getDate(2), "Not Scheduled", "Mr. Popeye", "123-456-9876", "oliveoil@smootchie.com", "I needs me spinach!", 1));
        courseList.add(new Course(SAMPLE_COURSE_3, getDate(2), getDate(3), "Not Scheduled", "Johnny Bravo", "1-800-captainluv", "captainluv@bravo.com", "Hey there pretty momma, come over here and be pretty next to me! wink wink", 1));
        return courseList;
    }

}
