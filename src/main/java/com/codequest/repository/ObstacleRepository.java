package com.codequest.repository;

import com.codequest.domain.Floor;
import com.codequest.domain.Obstacle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObstacleRepository extends JpaRepository<Obstacle, Long> {
    Optional<Obstacle> findByFloor(Floor floor);
    boolean existsByFloor(Floor floor);
}
