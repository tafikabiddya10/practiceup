package com.codequest.repository;

import com.codequest.domain.Assessment;
import com.codequest.domain.Obstacle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    List<Assessment> findByObstacleOrderByOrderIndexAsc(Obstacle obstacle);
    long countByObstacle(Obstacle obstacle);
}
