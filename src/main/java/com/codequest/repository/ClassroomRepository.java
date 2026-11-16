package com.codequest.repository;

import com.codequest.domain.Classroom;
import com.codequest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    List<Classroom> findByTeacherOrderByCreatedAtDesc(User teacher);
    Optional<Classroom> findByClassCode(String classCode);
    boolean existsByClassCode(String classCode);
}
