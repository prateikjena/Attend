package com.attend.timings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.attend.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomClassDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    private View v;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.bottom_sheet, container, false);

        String batch = setBatchSpinner();
        setSubjectSpinner(batch);

        Button cancel = v.findViewById(R.id.cancel);
        Button add = v.findViewById(R.id.addClass);
        
        cancel.setOnClickListener(this);
        add.setOnClickListener(this);
        
        return v;
    }

    private void setSubjectSpinner(String batch) {
        ArrayAdapter<String> adapter ;
        String[] firstYear = new String[] {
                "C", "Digital Logic", "C++", "data Structures"
        };

        String[] secondYear = new String[] {
                "Java", "Computer Networks", "Data Structures", "Operating System", "Android", "Computer Graphics", "HTML"
        };

        String[] thirdYear = new String[] {
                "Algorithms", "Microprocessor", "System programming", "Internet security", "Software Engineering", "Internet technology"
        };
        Spinner s = v.findViewById(R.id.subSpinner);

        switch (batch) {
            case "first" :
                adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, firstYear);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                break;
            case "second" :
                adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, secondYear);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                break;
            case "third" :
                adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, thirdYear);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + batch);
        }

        s.setAdapter(adapter);
    }

    private String setBatchSpinner() {
        String batch;
        String[] arraySpinner = new String[] {
                "1st Yr", "2nd Yr", "3rd Yr"
        };

        Spinner s = v.findViewById(R.id.batchSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        batch = s.getSelectedItem().toString();
        return batch;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                dismiss();
                break;
            case R.id.addClass:
                storeTimingAndNotify();
                dismiss();
                break;
        }
    }

    private void storeTimingAndNotify() {
    }
}
