package com.example.calendary;

import android.content.Context;
import android.util.TypedValue;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDecoration extends RecyclerView.ItemDecoration {

    private int size10;

    public ItemDecoration(Context context){
        size10 = dpToPx(context, 10);
    }

    private int dpToPx(Context context, int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
