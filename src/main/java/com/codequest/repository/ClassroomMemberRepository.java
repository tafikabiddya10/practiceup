package com.codequest.repository;

import com.codequest.domain.Classroom;
import com.codequest.domain.ClassroomMember;
import com.codequest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomMemberRepository extends JpaRepository<ClassroomMember, Long> {
    List<ClassroomMember> findByClassroomOrderByJoinedAtAsc(Classroom classroom);
    List<ClassroomMember> findByStudentOrderByJoinedAtDesc(User student);
    Optional<ClassroomMember> findByClassroomAndStudent(Classroom classroom, User student);
    boolean existsByClassroomAndStudent(Classroom classroom, User student);
    long countByClassroom(Classroom classroom);
}
