package com.example.calendary.Model;

import android.widget.ImageView;

import java.util.Date;

public class DiaryModel {

    public String username;
    public String title;
    public String content;
    public ImageView imageView;
    public String id;
    public String user_id;
    public Date timestamp;

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
