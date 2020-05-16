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

import com.slyfoxdevelopment.c196.R;
import com.slyfoxdevelopment.c196.TermEdit;
import com.slyfoxdevelopment.c196.database.Term;
import com.slyfoxdevelopment.c196.utils.Util;

import java.util.List;

import static com.slyfoxdevelopment.c196.utils.Constants.TERM_ID_KEY;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {

    private final List< Term > mTerms;

    private final Context mContext;

    public TermAdapter(List< Term > mTerms, Context mContext) {
        this.mTerms = mTerms;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.term_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Term term = mTerms.get(position);
        holder.termTitle.setText(term.getTitle());
        holder.termStartDate.setText(Util.convertDateForView(term.getStartDate()));
        holder.termEndDate.setText(Util.convertDateForView(term.getEndDate()));
        holder.termListEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TermEdit.class);
                intent.putExtra(TERM_ID_KEY, term.getId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mTerms != null)
            return mTerms.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView termTitle, termStartDate, termEndDate;
        private ImageButton termListEditButton;





        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            termTitle = itemView.findViewById(R.id.term_list_title);
            termStartDate = itemView.findViewById(R.id.term_list_start_date);
            termEndDate = itemView.findViewById(R.id.term_list_end_date);
            termListEditButton = itemView.findViewById(R.id.term_list_edit_button);
        }
    }
}
