package com.example.gadsleaderboardproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.gadsleaderboardproject.R;
import com.example.gadsleaderboardproject.Util;
import com.example.gadsleaderboardproject.models.LearnersModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SkillIQAdapter extends RecyclerView.Adapter<SkillIQAdapter.ViewHolder> {
    private List<LearnersModel> mLearnersRespons = new ArrayList<>();
    private OnDetailsListener mOnDetailsListener;

    public SkillIQAdapter(OnDetailsListener onDetailsListener) {
        mOnDetailsListener = onDetailsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.learner_model, parent, false);

        return new ViewHolder(itemView, mOnDetailsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LearnersModel learnersModel = mLearnersRespons.get(position);

        holder.leaner_name.setText(learnersModel.getName());
        holder.learner_score.setText(
                Util.skillIQToString(learnersModel.getScore()));
        holder.learner_country.setText(learnersModel.getCountry());
        //image

        if (learnersModel.getBadgeUrl() != null) {
            String imageUrl = learnersModel.getBadgeUrl();
//                    .replace("http://", "https://");
            Picasso.get()
                    .load(imageUrl)
                    .into(holder.learnerImage);
        }

    }

    @Override
    public int getItemCount() {
        return mLearnersRespons.size();
    }

    public void setIQResponses(List<LearnersModel> learnersRespons) {
        this.mLearnersRespons = learnersRespons;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.learnerName)
        TextView leaner_name;
        @BindView(R.id.learnerHours)
        TextView learner_score;
        @BindView(R.id.learnerCountry)
        TextView learner_country;
        @BindView(R.id.learner_Image)
        ImageView learnerImage;

        OnDetailsListener mOnDetailsListener;

        public ViewHolder(@NonNull View itemView, OnDetailsListener onDetailsListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //mBinding = DataBindingUtil.bind(itemView);
            this.mOnDetailsListener = onDetailsListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            mOnDetailsListener.onCardClick(getAdapterPosition());
        }
    }

    public interface OnDetailsListener {
        void onCardClick(int position);

    }
}
