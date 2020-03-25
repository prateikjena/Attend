package com.attend.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.attend.R;
import com.attend.attendance.TakeAttendance;

public class WelcomeFragment extends Fragment implements View.OnClickListener {

    private CardView takeAttendance, viewAttendance, setTimings, viewTimings, studentProfile;
    private View view;
    private String user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_welcome, container, false);
        initViews();

        Bundle bundle = getArguments();
        if (bundle != null) {
            user = bundle.getString("USER");
        }
        cardVisibility();

        setListeners();
        return view;
    }

    private void initViews() {
        takeAttendance = view.findViewById(R.id.TakeAttendance);
        viewAttendance = view.findViewById(R.id.AttendanceHistory);
        setTimings = view.findViewById(R.id.SetTimings);
        viewTimings = view.findViewById(R.id.classTimings);
        studentProfile = view.findViewById(R.id.StudentProfile);
    }

    private void setListeners() {
        takeAttendance.setOnClickListener(this);
        viewAttendance.setOnClickListener(this);
        setTimings.setOnClickListener(this);
        viewTimings.setOnClickListener(this);
        studentProfile.setOnClickListener(this);
    }

    private void cardVisibility() {
        if (user.equals("Student")) {
            takeAttendance.setVisibility(View.INVISIBLE);
            setTimings.setVisibility(View.INVISIBLE);
            studentProfile.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.AttendanceHistory :
                break;

            case R.id.classTimings:
                break;

            case R.id.TakeAttendance:
                startActivity(new Intent(getContext(), TakeAttendance.class));
                break;

            case R.id.SetTimings:
                break;

            case R.id.StudentProfile:
                break;
        }
    }

}
