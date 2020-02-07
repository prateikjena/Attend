package com.attend.attendance.cardView;

import androidx.cardview.widget.CardView;

public interface CardAdapter {
    int MAX_ELEVATION_FACTOR = 15;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
