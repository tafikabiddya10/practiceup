package com.codequest.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Records that a student revealed a specific hint.
 * Ensures the hint cost is deducted only once and hints are unlocked sequentially.
 */
@Entity
@Table(name = "hint_usages", uniqueConstraints = {
    @UniqueConstraint(name = "uk_student_hint", columnNames = {"student_id", "hint_id"})
})
public class HintUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hint_id", nullable = false)
    private Hint hint;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public HintUsage() {
    }

    public HintUsage(User student, Hint hint) {
        this.student = student;
        this.hint = hint;
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Hint getHint() {
        return hint;
    }

    public void setHint(Hint hint) {
        this.hint = hint;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
