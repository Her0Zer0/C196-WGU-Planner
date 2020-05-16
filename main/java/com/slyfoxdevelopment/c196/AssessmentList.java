package com.slyfoxdevelopment.c196;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.slyfoxdevelopment.c196.database.Assessment;
import com.slyfoxdevelopment.c196.ui.AssessmentAdapter;
import com.slyfoxdevelopment.c196.viewmodel.AssessmentViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import static com.slyfoxdevelopment.c196.utils.Constants.ASSOCIATED_COURSE_ID_KEY;
import static com.slyfoxdevelopment.c196.utils.Constants.IS_ASSESSMENT_NEW;
import static com.slyfoxdevelopment.c196.utils.Constants.SET_ASSESSMENT_TITLE;

public class AssessmentList extends AppCompatActivity {

    public static final String  TAG = "AssessmentList";

    RecyclerView mRecyclerView;
    private List< Assessment > mAssessments = new ArrayList<>();
    private AssessmentAdapter mAdapter;
    private AssessmentViewModel mViewModel;
    int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FloatingActionButton fab = findViewById(R.id.fab);



        mRecyclerView = findViewById(R.id.assessment_recycler);

        initRecyclerView();

        initViewModel();



















        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent intent = new Intent(getApplicationContext(), AssessmentEdit.class);
                intent.putExtra(ASSOCIATED_COURSE_ID_KEY, courseId);
                intent.putExtra(IS_ASSESSMENT_NEW, true);
                intent.putExtra(SET_ASSESSMENT_TITLE, "New Assessment");
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

        final Observer<List<Assessment>> assessmentObserver = new Observer< List< Assessment > >() {
            @Override
            public void onChanged(List< Assessment > assessments) {
                mAssessments.clear();

                for(Assessment assets: assessments){
//                    Log.i(TAG, "ID: " + assets.getAssociatedId() + " " + courseId);
                    if(assets.getAssociatedId() == courseId){
                        mAssessments.add(assets);
                    }

                }

                if(mAdapter == null){
                    mAdapter = new AssessmentAdapter(mAssessments, AssessmentList.this);
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    mAdapter.notifyDataSetChanged();
                }
            }
        };

        mViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        mViewModel.mAssessments.observe(this, assessmentObserver);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            courseId = extras.getInt(ASSOCIATED_COURSE_ID_KEY);
        }

    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }


}
