package com.example.calendary.diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendary.Model.DiaryModel;
import com.example.calendary.R;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewHolder> {
    private List<DiaryModel> diaryModelList;
    private OnDiaryAdapterListener mListener = null;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView, contentTextView, timeTextView, nameTextView;
        public ImageView profileView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.diarylist_timestamp);
            contentTextView = itemView.findViewById(R.id.diarylist_content);
            titleTextView = itemView.findViewById(R.id.diarylist_title);
            nameTextView = itemView.findViewById(R.id.diarylist_username);
        }
    }

    @NonNull
    @Override
    public DiaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diarylist_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }


    public void setOnDiaryAdapterListener(OnDiaryAdapterListener listener){
        this.mListener = listener;
    }


    public DiaryAdapter(List<DiaryModel> diaryModelList){
        this.diaryModelList = diaryModelList;
    }
    @Override
    public void onBindViewHolder(@NonNull DiaryAdapter.ViewHolder holder, final int position) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy.MM.dd");
//        String time = dateFormat.format(diaryModelList.get(position).timestamp);

        holder.titleTextView.setText(diaryModelList.get(position).title);
        holder.contentTextView.setText(diaryModelList.get(position).content);
        holder.nameTextView.setText(diaryModelList.get(position).username);
//        holder.timeTextView.setText(time);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onClickListener(v.getContext(), position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return diaryModelList.size();
    }

    public interface OnDiaryAdapterListener {
        public void onClickListener(Context context, int position);
    }
}
