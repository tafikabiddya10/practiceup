package com.codequest.repository;

import com.codequest.domain.Level;
import com.codequest.domain.LevelProgress;
import com.codequest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LevelProgressRepository extends JpaRepository<LevelProgress, Long> {
    Optional<LevelProgress> findByStudentAndLevel(User student, Level level);
    List<LevelProgress> findByStudent(User student);
    List<LevelProgress> findByLevel(Level level);
    boolean existsByLevel(Level level);
    long countByStudentAndCompletedTrue(User student);
}
