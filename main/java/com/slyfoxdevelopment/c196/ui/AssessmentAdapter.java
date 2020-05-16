package com.slyfoxdevelopment.c196.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.slyfoxdevelopment.c196.AssessmentEdit;
import com.slyfoxdevelopment.c196.R;
import com.slyfoxdevelopment.c196.database.Assessment;
import com.slyfoxdevelopment.c196.utils.Util;

import org.w3c.dom.Text;

import java.util.List;

import static com.slyfoxdevelopment.c196.utils.Constants.ASSESSMENT_ID_KEY;
import static com.slyfoxdevelopment.c196.utils.Constants.ASSOCIATED_COURSE_ID_KEY;
import static com.slyfoxdevelopment.c196.utils.Constants.IS_ASSESSMENT_NEW;
import static com.slyfoxdevelopment.c196.utils.Constants.SET_ASSESSMENT_TITLE;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.ViewHolder> {

    private final List< Assessment > mAssessments;

    private final String TAG = "AssessmentAdapter";

    private final Context mContext;

    public AssessmentAdapter(List< Assessment > mAssessments, Context mContext) {
        this.mAssessments = mAssessments;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.assessment_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Assessment assessment = mAssessments.get(position);

        holder.assessmentTitle.setText(assessment.getTitle());
        holder.assessmentStart.setText(Util.convertDateForView(assessment.getStartDate()));
        holder.assessmentStatus.setText(assessment.getAssessmentStatus());
        holder.assessmentType.setText(assessment.getAssessmentType());
        holder.assessmentEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send for our edit screen

                Intent intent = new Intent(mContext.getApplicationContext(), AssessmentEdit.class);
                intent.putExtra(IS_ASSESSMENT_NEW, false);
                intent.putExtra(ASSOCIATED_COURSE_ID_KEY, assessment.getAssociatedId());
                intent.putExtra(ASSESSMENT_ID_KEY, assessment.getId());
                intent.putExtra(SET_ASSESSMENT_TITLE, "Edit Assessment");
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mAssessments != null){
            return mAssessments.size();
        }else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView assessmentTitle, assessmentStart, assessmentType, assessmentStatus;
        ImageButton assessmentEditButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            assessmentEditButton = itemView.findViewById(R.id.list_edit_assessment);
            assessmentTitle = itemView.findViewById(R.id.list_assessment_title);
            assessmentStart = itemView.findViewById(R.id.list_assessment_start);
            assessmentType = itemView.findViewById(R.id.list_assessment_type);
            assessmentStatus = itemView.findViewById(R.id.list_assessment_status);

        }
    }


}
