package com.codequest.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * An Attempt records one submission by a student for an assessment.
 * This table is append-only: every try is retained for grading, streaks, and analytics.
 */
@Entity
@Table(name = "attempts")
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assessment_id", nullable = false)
    private Assessment assessment;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String submittedAnswer; // JSON submitted

    @Column(nullable = false)
    private boolean correct;

    @Column(nullable = false)
    private int scoreAwarded = 0;

    @Column(nullable = false)
    private int hintsUsed = 0;

    @Column(nullable = false)
    private int timeTakenSeconds = 0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Attempt() {
    }

    public Attempt(User student, Assessment assessment, String submittedAnswer, boolean correct, int scoreAwarded, int hintsUsed, int timeTakenSeconds) {
        this.student = student;
        this.assessment = assessment;
        this.submittedAnswer = submittedAnswer;
        this.correct = correct;
        this.scoreAwarded = scoreAwarded;
        this.hintsUsed = hintsUsed;
        this.timeTakenSeconds = timeTakenSeconds;
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

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public String getSubmittedAnswer() {
        return submittedAnswer;
    }

    public void setSubmittedAnswer(String submittedAnswer) {
        this.submittedAnswer = submittedAnswer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public int getScoreAwarded() {
        return scoreAwarded;
    }

    public void setScoreAwarded(int scoreAwarded) {
        this.scoreAwarded = scoreAwarded;
    }

    public int getHintsUsed() {
        return hintsUsed;
    }

    public void setHintsUsed(int hintsUsed) {
        this.hintsUsed = hintsUsed;
    }

    public int getTimeTakenSeconds() {
        return timeTakenSeconds;
    }

    public void setTimeTakenSeconds(int timeTakenSeconds) {
        this.timeTakenSeconds = timeTakenSeconds;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
