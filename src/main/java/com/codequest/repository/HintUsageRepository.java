package com.codequest.repository;

import com.codequest.domain.Hint;
import com.codequest.domain.HintUsage;
import com.codequest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HintUsageRepository extends JpaRepository<HintUsage, Long> {
    List<HintUsage> findByStudentOrderByCreatedAtAsc(User student);
    Optional<HintUsage> findByStudentAndHint(User student, Hint hint);
    boolean existsByStudentAndHint(User student, Hint hint);

    /**
     * True when any student has already revealed this hint.
     * Teachers may not delete a hint that has been used (it would orphan the
     * usage row and change a student's already-earned score).
     */
    boolean existsByHint(Hint hint);

    /**
     * Finds all hints used by a student for a specific level.
     */
    @Query("SELECT hu FROM HintUsage hu " +
           "WHERE hu.student = :student " +
           "AND hu.hint.obstacle.floor.level.id = :levelId")
    List<HintUsage> findByStudentAndLevelId(@Param("student") User student, @Param("levelId") Long levelId);
}
