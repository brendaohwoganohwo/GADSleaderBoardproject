package com.example.gadsleaderboardproject.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;

import com.example.gadsleaderboardproject.R;
import com.example.gadsleaderboardproject.apis.LeaderBoardService;
import com.example.gadsleaderboardproject.models.LearnersModel;
import com.example.gadsleaderboardproject.models.SubmitModel;
import com.example.gadsleaderboardproject.repository.Submit;
import com.google.android.material.bottomsheet.BottomSheetDialog;


import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.gadsleaderboardproject.repository.Submit.SubmitService.submitProject;

public class SubmitActivity extends AppCompatActivity {


    public static final String TAG = SubmitActivity.class.getSimpleName();
    private LeaderBoardService mLeaderBoardService;
    private MutableLiveData<List<LearnersModel>> mListMutableLiveData;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    String keyword;
    private BottomSheetDialog mDialog;
    private View mMyviews;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmaildress;
    private EditText mGitHubLink;
    private AlertDialog mMAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_project);
        Toolbar toolbar = findViewById(R.id.submissionToolBar);
        setSupportActionBar(toolbar);
        final Context context = this;

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final Button submit = findViewById(R.id.submitBtn);
        mFirstName = findViewById(R.id.firstName);
        mLastName = findViewById(R.id.lastName);
        mEmaildress = findViewById(R.id.emailAddress);
        mGitHubLink = findViewById(R.id.githubLink);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitActivity.this.projectSumit(mFirstName, mLastName, mEmaildress, mGitHubLink);

            }
        });
    }

    private void projectSumit(EditText firstName, EditText lastName, final EditText emaildress, EditText gitHubLink) {
        final SubmitModel projectSubmit = new SubmitModel();
        final String firs_name = firstName.getText().toString();
        final String last_name = lastName.getText().toString();
        final String email = emaildress.getText().toString();
        final String github = gitHubLink.getText().toString();

        if (TextUtils.isEmpty(firs_name)) {
            firstName.setError("Required");
        } else if (TextUtils.isEmpty(last_name)) {
            lastName.setError("Required");
        } else if (TextUtils.isEmpty(email)) {
            emaildress.setError("Required");
        } else if (TextUtils.isEmpty(github)) {
            gitHubLink.setError("Required");
        } else {
            openDialog(R.layout.confirm_dialog);

            projectSubmit.setFirstName(firs_name);
            projectSubmit.setLastName(last_name);
            projectSubmit.setEmailAddress(email);
            projectSubmit.setLintToProject(github);

            Button sure = mDialog.findViewById(R.id.sure_btn);
            ImageView cancel = mDialog.findViewById(R.id.cancel_submission);

            assert sure != null;
            sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Submit.SubmitService
                            .submitProject(firs_name, last_name, email, github)
                            .enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        openDialog(R.layout.success_dialog);
                                        Log.d(TAG, response.message());
                                        mFirstName.setText("");
                                        mLastName.setText("");
                                        mEmaildress.setText("");
                                        mGitHubLink.setText("");
                                    } else {
                                        openDialog(R.layout.failure_dialog);
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    openDialog(R.layout.failure_dialog);
                                    Log.d(TAG, t.getMessage(), t);
                                }

                            });
                }
            });
            assert cancel != null;
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });

        }

    }

    public void openDialog(int dialog_layout) {
        mDialog = new BottomSheetDialog(SubmitActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        mMyviews = inflater.inflate(dialog_layout, null);
        mDialog.setContentView(mMyviews);
        mDialog.show();
    }

}


