package com.codequest.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * An Obstacle represents the monster guarding a specific floor.
 * One floor has at most one obstacle in CodeQuest.
 */
@Entity
@Table(name = "obstacles", uniqueConstraints = {
    @UniqueConstraint(name = "uk_floor_obstacle", columnNames = {"floor_id"})
})
public class Obstacle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "floor_id", nullable = false, unique = true)
    private Floor floor;

    @Column(nullable = false, length = 64)
    private String monsterType; // e.g. GOBLIN, SKELETON, SLIME, DRAGON

    @Column(nullable = false)
    private String monsterName;

    @Column(columnDefinition = "TEXT")
    private String introText;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Obstacle() {
    }

    public Obstacle(Floor floor, String monsterType, String monsterName, String introText) {
        this.floor = floor;
        this.monsterType = monsterType;
        this.monsterName = monsterName;
        this.introText = introText;
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

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public void setMonsterType(String monsterType) {
        this.monsterType = monsterType;
    }

    public String getMonsterName() {
        return monsterName;
    }

    public void setMonsterName(String monsterName) {
        this.monsterName = monsterName;
    }

    public String getIntroText() {
        return introText;
    }

    public void setIntroText(String introText) {
        this.introText = introText;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
