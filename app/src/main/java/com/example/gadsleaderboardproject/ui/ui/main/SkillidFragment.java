package com.example.gadsleaderboardproject.ui.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gadsleaderboardproject.R;
import com.example.gadsleaderboardproject.adapters.LeaderBoardAdapter;
import com.example.gadsleaderboardproject.adapters.SkillIQAdapter;
import com.example.gadsleaderboardproject.models.LearnersModel;
import com.example.gadsleaderboardproject.viewmodel.LearnersViewModel;

import java.util.ArrayList;
import java.util.List;

public class SkillidFragment extends Fragment implements LeaderBoardAdapter.OnDetailsListener,
        SkillIQAdapter.OnDetailsListener {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private LearnersViewModel mLearnersViewModel;
    private SkillIQAdapter mIQBoardAdapter;
    private List<LearnersModel> mCardList;
    ProgressBar mProgressBar;

    public static SkillidFragment newInstance(int index) {
        SkillidFragment fragment = new SkillidFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCardList = new ArrayList<>();
        mIQBoardAdapter = new SkillIQAdapter(this);

//        mCompositeDisposable = new CompositeDisposable();

        mLearnersViewModel = new ViewModelProvider(this).get(LearnersViewModel.class);
        mLearnersViewModel.init();
        mLearnersViewModel.getSkillIQData().observe(this, new Observer<List<LearnersModel>>() {
            @Override
            public void onChanged(List<LearnersModel> skillIQresponse) {

                if (skillIQresponse != null) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mIQBoardAdapter.setIQResponses(mLearnersViewModel.getSkillIQData().getValue());
                    mIQBoardAdapter.notifyDataSetChanged();
                }

            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_leader_board, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.section_label);
        mProgressBar = root.findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mIQBoardAdapter);
        return root;
    }

    @Override
    public void onCardClick(int position) {

    }
}
