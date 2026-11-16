package com.codequest.repository;

import com.codequest.domain.Hint;
import com.codequest.domain.Obstacle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HintRepository extends JpaRepository<Hint, Long> {
    List<Hint> findByObstacleOrderByHintNumberAsc(Obstacle obstacle);
    Optional<Hint> findByObstacleAndHintNumber(Obstacle obstacle, int hintNumber);
}
