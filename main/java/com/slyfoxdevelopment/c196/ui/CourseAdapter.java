package com.slyfoxdevelopment.c196.ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.slyfoxdevelopment.c196.CourseEdit;
import com.slyfoxdevelopment.c196.R;
import com.slyfoxdevelopment.c196.database.Course;
import com.slyfoxdevelopment.c196.utils.Util;

import java.util.List;

import static com.slyfoxdevelopment.c196.utils.Constants.ASSOCIATED_ID_KEY;
import static com.slyfoxdevelopment.c196.utils.Constants.COURSE_ID_KEY;
import static com.slyfoxdevelopment.c196.utils.Constants.IS_COURSE_NEW;
import static com.slyfoxdevelopment.c196.utils.Constants.SET_COURSE_TITLE;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private final List< Course > mCourses;

    private static final String TAG = "CourseAdapter";

    private final Context mContext;

    public CourseAdapter(List< Course > mCourses, Context mContext) {
        this.mCourses = mCourses;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.course_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Course course = mCourses.get(position);
        holder.courseTitle.setText(course.getTitle());
        holder.courseStart.setText(Util.convertDateForView(course.getStartDate()));
        holder.courseEnd.setText(Util.convertDateForView(course.getEndDate()));
        holder.courseStatus.setText(course.getCourseStatus());

        Log.i(TAG, "Where are here");
        holder.courseEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CourseEdit.class);
                intent.putExtra(COURSE_ID_KEY, course.getId());
                intent.putExtra(IS_COURSE_NEW, false);
                intent.putExtra(SET_COURSE_TITLE, "Edit Course");
                intent.putExtra(ASSOCIATED_ID_KEY, course.getAssociate_id());
                mContext.startActivity(intent);
            }
        });

        holder.courseShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myMsg = "";
                myMsg += "Check out this course!\n\n";
                myMsg += "Course: " + course.getTitle() + "\n";
                myMsg += "Mentor: " + course.getMentor() + "\n";
                myMsg += "@Phone: " + course.getPhone() + "\n";
                myMsg += "@Email: " + course.getEmail() + "\n";
                myMsg += "Notes: " + course.getNotes() + "\n";
                Intent mShare = new Intent(Intent.ACTION_SEND);
                mShare.setType("text/plain");
                mShare.putExtra(Intent.EXTRA_SUBJECT, "Check out this course! " + course.getTitle());
                mShare.putExtra(Intent.EXTRA_TEXT, myMsg);
                mContext.startActivity(Intent.createChooser(mShare, "How do you wish to share?"));
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mCourses != null){
            return mCourses.size();
        }else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView courseTitle, courseStatus, courseStart, courseEnd;
        ImageButton courseEditButton, courseShareButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseTitle = itemView.findViewById(R.id.list_course_title);
            courseStatus = itemView.findViewById(R.id.list_course_status);
            courseStart = itemView.findViewById(R.id.list_course_start);
            courseEnd = itemView.findViewById(R.id.list_course_end);
            courseEditButton = itemView.findViewById(R.id.list_course_edit_button);
            courseShareButton = itemView.findViewById(R.id.list_course_share_button);

        }
    }
}
