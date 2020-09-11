package com.example.gadsleaderboardproject.apis;


import com.example.gadsleaderboardproject.models.LearnersModel;
import com.example.gadsleaderboardproject.models.SubmitModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LeaderBoardService {
    @GET("api/hours")
    Observable<List<LearnersModel>> getLeaners();
    @GET("api/skilliq")
    Observable<List<LearnersModel>> getSkillIQ();

    @POST("1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")
    @FormUrlEncoded
    Call<Void>submitProject(

            @Field("entry.1877115667") String firstName,
            @Field("entry.2006916086") String lastName,
            @Field("entry.1824927963") String emailAddress,
            @Field("entry.284483984") String linkToProject

    );
}
