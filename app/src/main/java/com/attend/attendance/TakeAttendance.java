package com.attend.attendance;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.attend.R;
import com.attend.attendance.cardView.CardFragmentPagerAdapter;
import com.attend.attendance.cardView.ShadowTransformer;

public class TakeAttendance extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_take_attendance, container, false);

        ViewPager mViewPager = view.findViewById(R.id.viewPager);

        CardFragmentPagerAdapter mFragmentCardAdapter = new CardFragmentPagerAdapter(getFragmentManager(), dpToPixels(getContext()));
        ShadowTransformer mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);

        mViewPager.setAdapter(mFragmentCardAdapter);
        mViewPager.setOffscreenPageLimit(3);

        return view;
    }

    private static float dpToPixels(Context context) {
        return 2 * (context.getResources().getDisplayMetrics().density);
    }

}
