package com.codequest.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Generic assessment posed by a monster obstacle.
 * Supports multiple types (MCQ, SCRAMBLE, FILL_BLANK) without schema modifications.
 * content: JSON data presented to students.
 * answerKey: JSON data holding correct solution; NEVER sent to browser.
 */
@Entity
@Table(name = "assessments")
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "obstacle_id", nullable = false)
    private Obstacle obstacle;

    @Column(nullable = false, length = 32)
    private String type; // e.g. MCQ, SCRAMBLE, FILL_BLANK

    @Column(nullable = false, columnDefinition = "TEXT")
    private String prompt;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content; // JSON

    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String answerKey; // JSON (server-side only)

    @Column(nullable = false)
    private int points = 10;

    @Column(nullable = false)
    private int orderIndex = 1;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Assessment() {
    }

    public Assessment(Obstacle obstacle, String type, String prompt, String content, String answerKey, int points, int orderIndex) {
        this.obstacle = obstacle;
        this.type = type;
        this.prompt = prompt;
        this.content = content;
        this.answerKey = answerKey;
        this.points = points;
        this.orderIndex = orderIndex;
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

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswerKey() {
        return answerKey;
    }

    public void setAnswerKey(String answerKey) {
        this.answerKey = answerKey;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
