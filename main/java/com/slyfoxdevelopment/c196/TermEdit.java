package com.slyfoxdevelopment.c196;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.slyfoxdevelopment.c196.database.Term;
import com.slyfoxdevelopment.c196.utils.Util;
import com.slyfoxdevelopment.c196.viewmodel.TermEditViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.view.View.GONE;
import static com.slyfoxdevelopment.c196.utils.Constants.ASSOCIATED_ID_KEY;
import static com.slyfoxdevelopment.c196.utils.Constants.EDITING_KEY;
import static com.slyfoxdevelopment.c196.utils.Constants.TERM_ID_KEY;

public class TermEdit extends AppCompatActivity {

    private TermEditViewModel mViewModel;

    private TextView editTermStartDate,editTermEndDate;
    private EditText editTermTitle;
    private Button saveBtn;
    private boolean mNewTerm, mEditing;
    private ImageButton endDateButton, startDateButton;
    private int termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //declare all the things we need
        //button to add courses
        FloatingActionButton fab = findViewById(R.id.fab);
        //edit form
        editTermStartDate = findViewById(R.id.edit_term_start_date);
        editTermEndDate = findViewById(R.id.edit_term_end_date);
        editTermTitle = findViewById(R.id.edit_term_title);
        saveBtn = findViewById(R.id.edit_term_save_btn);
        endDateButton = findViewById(R.id.end_date_button);
        startDateButton = findViewById(R.id.start_date_button);


        //check if the instance has been edited before updating on screen rotation
        if(savedInstanceState != null){
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }

        //initiate the view
        initViewModel();

        mViewModel.startAttachedCourseCount(termId);
        //when save button is clicked
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String termStartDate = editTermStartDate.getText().toString();
                String termEndDate = editTermEndDate.getText().toString();
                String termTitle = editTermTitle.getText().toString().trim();

                //when the save button is called we pass params to the editviewmodel
                //from editviewmodel we get the current obj for the mutablelive data obj
                // do our conversions on the dates and call an insert to teh repo
                //from the repo we run in the background with an executer and runnable
                //we then call the dao and insert the term

                if(!termEndDate.isEmpty() && !termStartDate.isEmpty() && !termTitle.isEmpty()){
                    mViewModel.saveTerm(termStartDate, termEndDate, termTitle);
                    onBackPressed();
                }else{
                    Toast.makeText(getApplicationContext(), "Please fill all inputs.",Toast.LENGTH_SHORT).show();
                }


            }
        });






        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mNewTerm){
                    Snackbar.make(view, "Please add a term before adding courses.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(), CourseList.class);
                    intent.putExtra(ASSOCIATED_ID_KEY, termId);
                    startActivity(intent);
                }

            }
        });


    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(TermEditViewModel.class);

        mViewModel.mLiveTerm.observe(this, term -> {

            if(term != null && !mEditing){
                String startDate = Util.convertDateForView(term.getStartDate());
                String endDate = Util.convertDateForView(term.getEndDate());
                editTermTitle.setText(term.getTitle());
                editTermStartDate.setText(startDate);
                editTermEndDate.setText(endDate);
            }

        });


        Bundle extras = getIntent().getExtras();

        if(extras == null){//set the title of the screen
            setTitle("New Term");
            mNewTerm = true;
            
        }else{
            setTitle("Edit Term");
            termId = extras.getInt(TERM_ID_KEY);
            mViewModel.loadTermData(termId);
        }




    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }


    Calendar c;
    DatePickerDialog dpd;

    public void onCalendarSet(View v){
        final TextView mStartDate = findViewById(R.id.edit_term_start_date),
                mEndDate = findViewById(R.id.edit_term_end_date);
        Calendar c;
        DatePickerDialog dpd;
        String id = v.getResources().getResourceName(v.getId());
        final String current = id.substring(id.indexOf("id/") + 3);
        c = Calendar.getInstance();
        int dd = c.get(Calendar.DAY_OF_MONTH);
        int mm = c.get(Calendar.MONTH);
        int yyyy = c.get(Calendar.YEAR);

        dpd = new DatePickerDialog(TermEdit.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                Log.i("Information: ", current );
                if(current.equalsIgnoreCase("start_date_button")){
                    mStartDate.setText( ( mMonth + 1 ) + "/" + mDay + "/" + mYear);
                }

                if(current.equalsIgnoreCase("end_date_button")){
                    mEndDate.setText( ( mMonth + 1 ) + "/" + mDay + "/" + mYear);
                }

            }
        }, yyyy, mm, dd);
        dpd.show();

    }


    public boolean onCreateOptionsMenu(Menu menu){

        if(!mNewTerm){
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu_edit, menu);
        }
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }else if(item.getItemId() == R.id.action_delete){

            int courseCount = mViewModel.getAttachedCourseCount();

            if(courseCount > 0){
                Toast.makeText(getApplicationContext(), "Courses are attached to the term, please delete the courses before deleting the term.",Toast.LENGTH_SHORT).show();
            }else{
                mViewModel.deleteTerm();
                finish();
            }


        }
        return true;
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }
}
