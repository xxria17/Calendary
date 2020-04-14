package com.example.calendary.calendar;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendary.R;

import java.util.Calendar;


public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    BaseCalendar baseCalendar;
    CalendarFragment calendarFragment;
    HolidayCalendar holidayCalendar;

    private OnCalendarAdapterListener mListener = null;

    private int select_position = -1;

    public CalendarAdapter(CalendarFragment fragment) {
        init();
        this.calendarFragment = fragment;
    }

    public void init(){
        baseCalendar = new BaseCalendar();
        baseCalendar.init();
        baseCalendar.initBaseCalendar();
        holidayCalendar = new HolidayCalendar();
    }

    public void setOnCalendarAdapterListener(OnCalendarAdapterListener listener) {
        this.mListener = listener;
    }

    //2. 그릴 뷰 인스턴스 요청
    @NonNull
    @Override
    public CalendarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.ViewHolder holder, final int position) {

//        // 공휴일 텍스트 컬러
//        ArrayList<Holidays> arrayList = HolidayCalendar.holidaysArrayList("2020");
//        arrayList.get(position).getDate();
//        holder.day.getResources().getColor(R.color.red);
        final String dayString = baseCalendar.data.get(position).toString();

        if(select_position == position){
            holder.day.setBackgroundResource(R.color.colorPrimary);
            holder.day.setTextColor(Color.parseColor("#FFFFFF"));
        } else if(position % BaseCalendar.DAYS_OF_WEEK == 0) {
            holder.day.setBackgroundResource(R.color.transparent);
            holder.day.setTextColor(Color.parseColor("#FF1200"));
        } else {
            holder.day.setTextColor(Color.parseColor("#676d6e"));
        }

        holder.day.setAlpha(1f);
        holder.day.setText(dayString);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null) {
                    mListener.onClickListener(v.getContext(), dayString, position);
                }
            }
        });
    }

    //1. 몇개 그릴건지 확인하는 메소드
    @Override
    public int getItemCount() {
        int count = baseCalendar.data.size();
        Log.d("leminity", "check to getItemCount : " + count);
        return count;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView day;

        public ViewHolder(View v){
            super(v);
            day = v.findViewById(R.id.calendar_date);
        }
    }

    public void changeToPrevMonth(){
        baseCalendar.changeToPrevMonth();
        refreshView(baseCalendar.calendar);
    }

    public void changeToNextMonth(){
        baseCalendar.changeToNextMonth();
        refreshView(baseCalendar.calendar);
    }

    public void refreshView(Calendar calendar) {
        notifyDataSetChanged();
    }

    public interface OnCalendarAdapterListener {
        public void onClickListener(Context ctx, String day, int position);
    }
}
