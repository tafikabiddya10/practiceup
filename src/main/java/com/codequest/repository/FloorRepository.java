package com.codequest.repository;

import com.codequest.domain.Floor;
import com.codequest.domain.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {
    List<Floor> findByLevelOrderByFloorNumberAsc(Level level);
    Optional<Floor> findByLevelAndFloorNumber(Level level, int floorNumber);
    long countByLevel(Level level);
}
