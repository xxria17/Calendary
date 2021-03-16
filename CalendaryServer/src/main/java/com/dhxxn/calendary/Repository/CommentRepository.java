package com.dhxxn.calendary.Repository;

import com.dhxxn.calendary.DTO.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
