package com.slyfoxdevelopment.c196;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.slyfoxdevelopment.c196.database.Assessment;
import com.slyfoxdevelopment.c196.utils.Notify;
import com.slyfoxdevelopment.c196.utils.Util;
import com.slyfoxdevelopment.c196.viewmodel.CourseEditViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Observer;

import static com.slyfoxdevelopment.c196.utils.Constants.ASSOCIATED_COURSE_ID_KEY;
import static com.slyfoxdevelopment.c196.utils.Constants.ASSOCIATED_ID_KEY;
import static com.slyfoxdevelopment.c196.utils.Constants.COURSE_ID_KEY;
import static com.slyfoxdevelopment.c196.utils.Constants.EDITING_KEY;
import static com.slyfoxdevelopment.c196.utils.Constants.IS_COURSE_NEW;
import static com.slyfoxdevelopment.c196.utils.Constants.SET_COURSE_TITLE;

public class CourseEdit extends AppCompatActivity {

    private static final String TAG = "CourseEdit";

    private CourseEditViewModel mViewModel;
    private NotificationManagerCompat managerCompat;

    private TextView editCourseStart, editCourseEnd;
    private EditText editCourseTitle, editMentor, editPhone,editEmail, editNotes;
    private Spinner spinner;
    ArrayAdapter<String> spinnerAdapter;
    private boolean mNewCourse, mEditing;
    private ImageButton endDateButton, startDateButton;
    private int courseId;
    Button saveBtn;
    private int associatedId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //declare all the things we need
        FloatingActionButton fab = findViewById(R.id.fab);
        editCourseStart = findViewById(R.id.edit_course_start);
        editCourseEnd = findViewById(R.id.edit_course_end);
        editCourseTitle = findViewById(R.id.edit_course_title);
        editMentor = findViewById(R.id.edit_course_mentor);
        editPhone = findViewById(R.id.edit_course_phone);
        editEmail = findViewById(R.id.edit_course_email);
        editNotes = findViewById(R.id.edit_course_notes);
        spinner = findViewById(R.id.edit_course_status);
        saveBtn = findViewById(R.id.edit_course_save_btn);


        initSpinner();


        if(savedInstanceState != null){
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }

        managerCompat = NotificationManagerCompat.from(this);

        initViewModel();


        saveBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String courseStartDate = editCourseStart.getText().toString();
                String courseEndDate = editCourseEnd.getText().toString();
                String courseTitle = editCourseTitle.getText().toString().trim();
                String courseStatus = spinner.getSelectedItem().toString();
                String mentor = editMentor.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String notes = editNotes.getText().toString().trim();

                if(courseStartDate.length() == 0 || courseEndDate.length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "Please fill out all dates.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(courseTitle.length() == 0 || mentor.length() == 0 || phone.length() == 0 || email.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill out all inputs.", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(courseStatus.equalsIgnoreCase("scheduled") || courseStatus.equalsIgnoreCase("passed")){
                    showStatusNotification(courseStatus , courseTitle, courseStartDate, courseEndDate);
                }


                    mViewModel.saveCourse(courseTitle, courseStartDate, courseEndDate, courseStatus, mentor, phone, email, notes, associatedId);
                    onBackPressed();
            }
        });






        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //go to the attachments screen

                if(mNewCourse){
                    Snackbar.make(view, "Please add a course before adding assessments.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    //go to the assessments list
                    Intent intent = new Intent(getApplicationContext(), AssessmentList.class);
                    intent.putExtra(ASSOCIATED_COURSE_ID_KEY, courseId);
                    startActivity(intent);
                }
            }
        });
    }

    private void showStatusNotification(String courseStatus, String courseTitle, String startDate, String endDate) {
        if(courseStatus.equalsIgnoreCase("scheduled")){
            String msg = "Course: " + courseTitle + "\nScheduled to begin " + startDate + "\n and end " + endDate;

            Notification notification1 = new NotificationCompat.Builder(this, Notify.CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Course Notification")
                    .setContentText(msg)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setCategory(NotificationCompat.CATEGORY_REMINDER)
                    .build();

            managerCompat.notify(1, notification1);
        }else{
//            courseStatus.equalsIgnoreCase("passed")
            String msg = "Congrats for passing " + courseTitle + "! Continue to keep moving forward!";
            Notification notification1 = new NotificationCompat.Builder(this, Notify.CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Course Notification")
                    .setContentText(msg)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setCategory(NotificationCompat.CATEGORY_REMINDER)
                    .build();

            managerCompat.notify(1, notification1);
        }
    }

    private void initViewModel() {

        mViewModel = ViewModelProviders.of(this).get(CourseEditViewModel.class);

        mViewModel.mLiveCourse.observe(this, course -> {
            if(course != null && !mEditing){
                String startDate = Util.convertDateForView(course.getStartDate());
                String endDate = Util.convertDateForView(course.getEndDate());
                editCourseTitle.setText(course.getTitle());
                editMentor.setText(course.getMentor());
                editPhone.setText(course.getPhone());
                editEmail.setText(course.getEmail());
                editNotes.setText(course.getNotes());
                editCourseStart.setText(startDate);
                editCourseEnd.setText(endDate);

                if(course.getCourseStatus() != null){
                    int spinnerPosition = spinnerAdapter.getPosition(course.getCourseStatus());
                    spinner.setSelection(spinnerPosition);
                }



            }
        });

        Bundle extras = getIntent().getExtras();
        //We will always need to check for an associated id
        //and set the title and check for new course or old one
        if(extras != null){
            String frameTitle = extras.getString(SET_COURSE_TITLE);
            setTitle(frameTitle);
            mNewCourse = extras.getBoolean(IS_COURSE_NEW);
            associatedId = extras.getInt(ASSOCIATED_ID_KEY);
            courseId = extras.getInt(COURSE_ID_KEY);
            mViewModel.loadCourseData(courseId);
        }

    }








    private void initSpinner() {

        String[] status = getResources().getStringArray(R.array.status_array);
        spinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner,status);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner);
        spinner.setAdapter(spinnerAdapter);
    }

    Calendar c;
    DatePickerDialog dpd;


    public void onCalendarClick(View view){
        String id = view.getResources().getResourceName(view.getId());
        final String current = id.substring(id.indexOf("id/") + 3);

        c = Calendar.getInstance();
        int dd = c.get(Calendar.DAY_OF_MONTH);
        int mm = c.get(Calendar.MONTH);
        int yyyy = c.get(Calendar.YEAR);

        dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {

                if(current.equalsIgnoreCase("show_cal_start_date")){
                    editCourseStart.setText( ( mMonth + 1 ) + "/" + mDay + "/" + mYear);
                }

                if(current.equalsIgnoreCase("show_cal_end_date")){
                    editCourseEnd.setText( ( mMonth + 1 ) + "/" + mDay + "/" + mYear);
                }

            }
        }, yyyy, mm, dd);
        dpd.show();

    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outstate){
        outstate.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outstate);
    }

    public boolean onCreateOptionsMenu(Menu menu){

        if(!mNewCourse){
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu_edit, menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        } else if(item.getItemId() == R.id.action_delete){
            mViewModel.deleteCourse();
            finish();
        }

        return true;
    }

}
