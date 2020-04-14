package com.example.calendary.diary;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.Date;

public class DiaryModel implements Serializable {

    public String username;
    public String title;
    public String content;
    public ImageView imageView;
    public String id;
    public String user_id;
    public Date timestamp;
    public Date select_timestamp;

    public DiaryModel(){}

    @Override
    public String toString() {
        return "DiaryModel{" +
                "username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imageView=" + imageView +
                '}';
    }


}
