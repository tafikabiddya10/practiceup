package com.codequest.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * A progressive hint attached to an Obstacle (monster).
 * Each obstacle can have up to 3 ordered hints (hintNumber 1, 2, 3):
 * 1: Nudge, 2: Concept Reminder, 3: Near-Solution.
 */
@Entity
@Table(name = "hints", uniqueConstraints = {
    @UniqueConstraint(name = "uk_obstacle_hint_number", columnNames = {"obstacle_id", "hint_number"})
})
public class Hint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "obstacle_id", nullable = false)
    private Obstacle obstacle;

    @Column(name = "hint_number", nullable = false)
    private int hintNumber; // 1, 2, or 3

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(name = "point_cost", nullable = false)
    private int pointCost = 0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Hint() {
    }

    public Hint(Obstacle obstacle, int hintNumber, String text, int pointCost) {
        this.obstacle = obstacle;
        this.hintNumber = hintNumber;
        this.text = text;
        this.pointCost = pointCost;
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

    public int getHintNumber() {
        return hintNumber;
    }

    public void setHintNumber(int hintNumber) {
        this.hintNumber = hintNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPointCost() {
        return pointCost;
    }

    public void setPointCost(int pointCost) {
        this.pointCost = pointCost;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
