package com.example.calendary;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDecoration extends RecyclerView.ItemDecoration {

    private int size10;
    private int size5;

    public ItemDecoration(Context context){
        size10 = dpToPx(context, 40);
        size5 = dpToPx(context, 13);
    }

    private int dpToPx(Context context, int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state){
        super.getItemOffsets(outRect, view, parent,state);

        int position = parent.getChildAdapterPosition(view);
        int itemCount = state.getItemCount();

        if(position == 0 || position == 1){
            outRect.top = size10;
            outRect.bottom = size10;
        } else {
            outRect.top = size10;
            outRect.bottom = size10;
        }

        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        int spanIndex = layoutParams.getSpanIndex();

        outRect.left = size5;
        outRect.right = size5;



    }
}
