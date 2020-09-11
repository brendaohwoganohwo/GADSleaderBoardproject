package com.example.gadsleaderboardproject.repository;



import com.example.gadsleaderboardproject.apis.LeaderBoardService;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Submit {

    public static class SubmitService {
        private LeaderBoardService mLeaderBoardService;
        private static final String SUBMISION_URL = "https://docs.google.com/forms/d/e/";
        public static Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(SUBMISION_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        public static LeaderBoardService sLeaderBoardService =
                retrofit.create(LeaderBoardService.class);

        public static Call<Void> submitProject(String fName, String lName, String email, String link) {
            return sLeaderBoardService.submitProject(fName, lName, email, link);
        }

    }}
