package com.slyfoxdevelopment.c196;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.slyfoxdevelopment.c196.utils.Notify;
import com.slyfoxdevelopment.c196.utils.Util;
import com.slyfoxdevelopment.c196.viewmodel.AssessmentEditViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProviders;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static com.slyfoxdevelopment.c196.utils.Constants.ASSESSMENT_ID_KEY;
import static com.slyfoxdevelopment.c196.utils.Constants.ASSOCIATED_COURSE_ID_KEY;
import static com.slyfoxdevelopment.c196.utils.Constants.EDITING_KEY;
import static com.slyfoxdevelopment.c196.utils.Constants.IS_ASSESSMENT_NEW;
import static com.slyfoxdevelopment.c196.utils.Constants.SET_ASSESSMENT_TITLE;

public class AssessmentEdit extends AppCompatActivity {

    private AssessmentEditViewModel mViewModel;

    private TextView editAssetTitle, editAssetStart;
    private Spinner editAssetType, editAssetStatus;

    private ArrayAdapter< String > statusAdapter;
    private ArrayAdapter< String > typeAdapter;

    private boolean mNewAsset, mEditing;
    private int associatedCourseId;
    private int assessmentId;

    private Button editSaveBtn;

    private NotificationManagerCompat managerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);

        editAssetTitle = findViewById(R.id.edit_assessment_title);
        editAssetStart = findViewById(R.id.edit_assessment_start);
        editAssetStatus = findViewById(R.id.edit_assessment_status);
        editAssetType = findViewById(R.id.edit_assessment_type);
        editSaveBtn = findViewById(R.id.edit_asset_save_btn);


        initSpinner();

        if(savedInstanceState != null){
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }
        managerCompat = NotificationManagerCompat.from(this);

        initViewModel();


        editSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editAssetTitle.getText().toString().trim();
                String startDate = editAssetStart.getText().toString();
                String assetStatus = editAssetStatus.getSelectedItem().toString();
                String assetType = editAssetType.getSelectedItem().toString();

                if(!title.isEmpty() && !startDate.isEmpty()){

                    if(assetStatus.equalsIgnoreCase("scheduled") || assetStatus.equalsIgnoreCase("passed")){
                            showAssetNotification(assetStatus, title, startDate);
                    }


                    mViewModel.saveAssessment(title, startDate, assetType, assetStatus, associatedCourseId);
                    onBackPressed();
                }else{
                    Toast.makeText(getApplicationContext(), "Please fill out all inputs.",Toast.LENGTH_SHORT).show();
                }
            }
        });





        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Peek a Boo! O-O", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void showAssetNotification(String assetStatus, String title, String startDate) {

        if(assetStatus.equalsIgnoreCase("scheduled")){
            String msg = title + " is scheduled for " + startDate;
            Notification notification2 = new NotificationCompat.Builder(this, Notify.CHANNEL_2_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Assessment Notification")
                    .setContentText(msg)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();

            managerCompat.notify(1, notification2);

        }else{
//             || assetStatus.equalsIgnoreCase("passed")
            String msg = "Congrats on passing " + title + "!";
            Notification notification2 = new NotificationCompat.Builder(this, Notify.CHANNEL_2_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Assessment Notification")
                    .setContentText(msg)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();

            managerCompat.notify(1, notification2);
        }
    }

    private void initViewModel() {

        mViewModel = ViewModelProviders.of(this).get(AssessmentEditViewModel.class);

        mViewModel.mLiveAssessments.observe(this, assessment -> {
            if(assessment != null && !mEditing){
                String startDate = Util.convertDateForView(assessment.getStartDate());
                editAssetTitle.setText(assessment.getTitle());
                editAssetStart.setText(startDate);


                if(assessment.getAssessmentStatus() != null){
                    int spinnerStatusPosition = statusAdapter.getPosition(assessment.getAssessmentStatus());
                    editAssetStatus.setSelection(spinnerStatusPosition);
                }

                if(assessment.getAssessmentType() != null){
                    int spinnerTypePosition = typeAdapter.getPosition(assessment.getAssessmentType());
                    editAssetType.setSelection(spinnerTypePosition);
                }



            }
        });

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            String frameTitle = extras.getString(SET_ASSESSMENT_TITLE);
            setTitle(frameTitle);
            mNewAsset = extras.getBoolean(IS_ASSESSMENT_NEW);
            associatedCourseId = extras.getInt(ASSOCIATED_COURSE_ID_KEY);
            assessmentId = extras.getInt(ASSESSMENT_ID_KEY);
            mViewModel.loadAssessmentContent(assessmentId);
        }
    }

    private void initSpinner() {
        String[] type_array = getResources().getStringArray(R.array.type_array);
        String[] status_array = getResources().getStringArray(R.array.status_array);

        typeAdapter = new ArrayAdapter<>(this, R.layout.spinner, type_array);
        typeAdapter.setDropDownViewResource(R.layout.spinner);
        editAssetType.setAdapter(typeAdapter);


        statusAdapter = new ArrayAdapter<>(this, R.layout.spinner, status_array);
        statusAdapter.setDropDownViewResource(R.layout.spinner);
        editAssetStatus.setAdapter(statusAdapter);
    }

    Calendar c;
    DatePickerDialog dpd;


    public void onCalendarClick(View view){
        c = Calendar.getInstance();
        int dd = c.get(Calendar.DAY_OF_MONTH);
        int mm = c.get(Calendar.MONTH);
        int yyyy = c.get(Calendar.YEAR);

        dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                editAssetStart.setText( ( mMonth + 1 ) + "/" + mDay + "/" + mYear);
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

        if(!mNewAsset){
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
            mViewModel.deleteAssessment();
            finish();
        }

        return true;
    }

}
