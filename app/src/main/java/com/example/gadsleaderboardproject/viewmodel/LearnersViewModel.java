package com.example.gadsleaderboardproject.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.gadsleaderboardproject.models.LearnersModel;
import com.example.gadsleaderboardproject.repository.LeasderBoardRepository;

import java.util.List;

public class LearnersViewModel extends AndroidViewModel {
    private LiveData<List<LearnersModel>> mLearningHours;
    private LiveData<List<LearnersModel>> mSkillIQ;

    public LearnersViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        if (mLearningHours != null) {
            return;
        }
        LeasderBoardRepository leasderBoardRepository = new LeasderBoardRepository();
        mLearningHours = leasderBoardRepository.getLeanersMutableLiveData();

        if(mSkillIQ!=null){
            return;
        }
        LeasderBoardRepository skillIqBoard = new LeasderBoardRepository();
        mSkillIQ = skillIqBoard.getSkilligMutableLiveData();
    }

    public LiveData<List<LearnersModel>> getLearnersData() {
        return mLearningHours;
    }
    public LiveData<List<LearnersModel>> getSkillIQData() {
        return mSkillIQ;
    }
}

