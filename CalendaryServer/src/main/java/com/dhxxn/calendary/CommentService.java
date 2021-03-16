package com.dhxxn.calendary;

import com.dhxxn.calendary.DTO.Comment;
import com.dhxxn.calendary.Repository.CommentRepository;
import com.dhxxn.calendary.Repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    private final DiaryRepository diaryRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(DiaryRepository diaryRepository, CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
        this.diaryRepository = diaryRepository;
    }

    public List<Comment> findAllByDiary(Integer id) {
        List<Comment> comments = new ArrayList<>();
        List<Comment> all = commentRepository.findAll();

        for (Comment comment: all) {
            if (comment.getBoard().getId().equals(id)) {
                comments.add(comment);
            }
        }
        return comments;
    }

    public Comment findById(Integer id) {
        return commentRepository.findById(id).get();
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public void delete(Integer id) {
        commentRepository.delete(findById(id));
    }
}
