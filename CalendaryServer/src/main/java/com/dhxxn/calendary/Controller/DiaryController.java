package com.dhxxn.calendary.Controller;

import com.dhxxn.calendary.CommentService;
import com.dhxxn.calendary.DTO.Comment;
import com.dhxxn.calendary.DTO.Diary;
import com.dhxxn.calendary.Model.APIDiary;
import com.dhxxn.calendary.Repository.CommentRepository;
import com.dhxxn.calendary.Repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/diary")
public class DiaryController {

    private final DiaryRepository diaryRepository;
    private final CommentService commentService;

    @Autowired
    public DiaryController(DiaryRepository diaryRepository, CommentService commentService) {
        this.diaryRepository = diaryRepository;
        this.commentService = commentService;
    }

    @GetMapping("/")
    public List<Diary> getAllDiary() {
        return diaryRepository.findAll();
    }

    @GetMapping("/{id}")
    public APIDiary get(@PathVariable Integer id) {
        Diary diary = diaryRepository.findById(id).get();
        List<Comment> commentList = commentService.findAllByDiary(id);
        return new APIDiary(diary, commentList);
    }

    @PostMapping("/")
    public Diary create(@ModelAttribute Diary diary) {
        return diaryRepository.save(diary);
    }

    @DeleteMapping("/{id}")
    public void deleteDiary(@PathVariable Integer id) {
        diaryRepository.delete(diaryRepository.findById(id).get());
    }

    @PutMapping("/{id}")
    public Diary update(@PathVariable Integer id, @ModelAttribute final Diary newDiary) {
        Diary diary = diaryRepository.findById(id).get();
        if (newDiary.getTitle() != null) {
            diary.setTitle(newDiary.getTitle());
        }
        if (newDiary.getContent() != null) {
            diary.setContent(newDiary.getContent());
        }

        return diaryRepository.save(diary);
    }

    @PostMapping("/{diaryId}/comment")
    public Comment reply(@ModelAttribute Comment comment, @PathVariable Integer diaryId) {
        comment.setBoard(diaryRepository.findById(diaryId).get());
        return commentService.save(comment);
    }
}
