package com.slyfoxdevelopment.c196;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.slyfoxdevelopment.c196.database.Term;
import com.slyfoxdevelopment.c196.ui.TermAdapter;
import com.slyfoxdevelopment.c196.viewmodel.TermViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class TermList extends AppCompatActivity {


    public static final String TAG = "TermList";


    RecyclerView mRecyclerView;
    private List< Term > mTermList = new ArrayList<>();
    private TermAdapter mAdapter;
    private TermViewModel mViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        setTitle("Term List");
        mRecyclerView = findViewById(R.id.term_recycler);


        initRecyclerView();
        initViewModel();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), TermEdit.class);
            startActivity(intent);
        });


    }


    private void initViewModel() {
        final Observer<List<Term>> termObserver = new Observer< List< Term > >() {
            @Override
            public void onChanged(List< Term > terms) {
                mTermList.clear();
                mTermList.addAll(terms);

                if(mAdapter == null) {
                    mAdapter = new TermAdapter(mTermList, TermList.this);
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    mAdapter.notifyDataSetChanged();
                }
            }
        };

        mViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        mViewModel.mTerms.observe(this, termObserver); //subscribing to the data

    }

    private void initRecyclerView() {

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_sample_data) {
            
            addSampleData();
            return true;
        }else if(id == R.id.action_delete_all){
            deleteAllNotes();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllNotes() {
        mViewModel.deleteAllNotes();
    }

    private void addSampleData() {

        mViewModel.addSampleData();
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
