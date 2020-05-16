package com.slyfoxdevelopment.c196;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.slyfoxdevelopment.c196.database.Course;
import com.slyfoxdevelopment.c196.ui.CourseAdapter;
import com.slyfoxdevelopment.c196.viewmodel.CourseViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static com.slyfoxdevelopment.c196.utils.Constants.ASSOCIATED_ID_KEY;
import static com.slyfoxdevelopment.c196.utils.Constants.IS_COURSE_NEW;
import static com.slyfoxdevelopment.c196.utils.Constants.SET_COURSE_TITLE;
import static com.slyfoxdevelopment.c196.utils.Constants.TERM_ID_KEY;

public class CourseList extends AppCompatActivity {


    public static final String TAG = "CourseList";

    RecyclerView mRecyclerView;
    private List< Course > mCourseList = new ArrayList<>();
    private List<Course > filteredList = new ArrayList<>();
    private CourseAdapter mAdapater;
    private CourseViewModel mViewModel;
    int termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);

        mRecyclerView = findViewById(R.id.course_recycler);



        initRecyclerView();
        initViewModel();








        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext(), CourseEdit.class);
                intent.putExtra(ASSOCIATED_ID_KEY, termId);
                intent.putExtra(IS_COURSE_NEW, true);
                intent.putExtra(SET_COURSE_TITLE, "New Course");
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void initViewModel() {



        final Observer<List<Course>> courseObserver = new Observer< List< Course > >() {
            @Override
            public void onChanged(List< Course > courses) {

                filteredList.clear();

                for(Course course: courses){

                    if(course.getAssociate_id() == termId){
                        filteredList.add(course);
                    }


                }

                if(mAdapater == null){
                    mAdapater = new CourseAdapter(filteredList, CourseList.this);
                    mRecyclerView.setAdapter(mAdapater);
                }else{
                    mAdapater.notifyDataSetChanged();
                }
            }
        };

        mViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        mViewModel.mCourses.observe(this, courseObserver);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            termId = extras.getInt(ASSOCIATED_ID_KEY);
            mViewModel.setAssociateId(termId);
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }




    //TODO: EVERYTHING BUT ADD THE COURSE AND COURSE DAO

    //4. ADD ASSESSMENTS ETC.
}
