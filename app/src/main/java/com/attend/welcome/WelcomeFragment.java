package com.attend.welcome;

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
import com.attend.attendance.Batches;
import com.attend.timings.AddTimings;

public class WelcomeFragment extends Fragment implements View.OnClickListener {

    private CardView takeAttendance, viewAttendance, setTimings, viewTimings, studentProfile;
    private View view;
    private String user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_welcome, container, false);
        initViews();

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.AttendanceHistory :
                break;

            case R.id.classTimings:
                break;

            case R.id.TakeAttendance:
                startActivity(new Intent(getContext(), Batches.class));
                break;

            case R.id.SetTimings:
                startActivity(new Intent(getContext(), AddTimings.class));
                break;

            case R.id.StudentProfile:
                break;
        }
    }

}
