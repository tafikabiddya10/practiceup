package com.codequest.repository;

import com.codequest.domain.Comment;
import com.codequest.domain.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByLevelOrderByCreatedAtAsc(Level level);
    long countByLevel(Level level);
}
