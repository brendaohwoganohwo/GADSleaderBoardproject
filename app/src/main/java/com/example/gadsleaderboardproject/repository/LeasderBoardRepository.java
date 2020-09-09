package com.example.gadsleaderboardproject.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.gadsleaderboardproject.apis.LeaderBoardService;
import com.example.gadsleaderboardproject.models.LearnersModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LeasderBoardRepository {
    private static final String LEADERBOARD_URL = "https://gadsapi.herokuapp.com/";

    private LeaderBoardService mLeaderBoardService;
    private MutableLiveData<List<LearnersModel>> mListMutableLiveData;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    String keyword;
    Context mContext;

    public LeasderBoardRepository() {
        mListMutableLiveData = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();
        mLeaderBoardService = new retrofit2.Retrofit.Builder()
                .baseUrl(LEADERBOARD_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(LeaderBoardService.class);

    }


    public void getLeaners() {
        mLeaderBoardService.getLeaners()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<LearnersModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<LearnersModel> learnersModel) {
                        mListMutableLiveData.postValue(learnersModel);

                    }

                    @Override
                    public void onError(Throwable e) {
                        mListMutableLiveData.postValue(null);
                    }


                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void getSkillIQ() {
        mLeaderBoardService.getSkillIQ()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<LearnersModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<LearnersModel> learnersModel) {
                        mListMutableLiveData.postValue(learnersModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListMutableLiveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public LiveData<List<LearnersModel>> getLeanersMutableLiveData() {
        getLeaners();
        return mListMutableLiveData;
    }

    public LiveData<List<LearnersModel>> getSkilligMutableLiveData() {
        getSkillIQ();
        return mListMutableLiveData;
    }

}
