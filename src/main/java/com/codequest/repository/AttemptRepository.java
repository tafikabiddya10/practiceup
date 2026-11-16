package com.codequest.repository;

import com.codequest.domain.Assessment;
import com.codequest.domain.Attempt;
import com.codequest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    List<Attempt> findByStudentAndAssessmentOrderByCreatedAtDesc(User student, Assessment assessment);
    List<Attempt> findByStudentOrderByCreatedAtDesc(User student);
    boolean existsByStudentAndAssessmentAndCorrectTrue(User student, Assessment assessment);
    long countByAssessment(Assessment assessment);
}
