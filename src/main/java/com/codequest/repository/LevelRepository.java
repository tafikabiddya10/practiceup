package com.codequest.repository;

import com.codequest.domain.Level;
import com.codequest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
    List<Level> findByTeacherOrderByCreatedAtDesc(User teacher);
    List<Level> findByPublishedTrueOrderByCreatedAtDesc();

    /**
     * Finds published levels created by teachers whose classes the given student has joined.
     */
    @Query("SELECT DISTINCT l FROM Level l " +
           "WHERE l.published = true AND l.teacher IN (" +
           "  SELECT c.teacher FROM ClassroomMember cm JOIN cm.classroom c WHERE cm.student = :student" +
           ") ORDER BY l.createdAt DESC")
    List<Level> findAvailableForStudent(@Param("student") User student);
}
