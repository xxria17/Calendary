package com.dhxxn.calendary.Model;

import com.dhxxn.calendary.DTO.Comment;
import com.dhxxn.calendary.DTO.Diary;
import com.dhxxn.calendary.DTO.User;

import java.time.LocalDateTime;
import java.util.List;

public class APIDiary {

    private Integer id;
    private String title;
    private User writer;
    private String content;
    private LocalDateTime writeTime;
    private String calendarTime;
    private List<Comment> commentList;

    public APIDiary(Diary diary, List<Comment> commentList) {
        this.id = diary.getId();
        this.writer = diary.getWriter();
        this.title = diary.getTitle();
        this.content = diary.getContent();
        this.writeTime = diary.getWriteTime();
        this.calendarTime = diary.getCalendarTime();
        this.commentList = commentList;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getWriteTime() {
        return writeTime;
    }

    public String getCalendarTime() {
        return calendarTime;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }
}
