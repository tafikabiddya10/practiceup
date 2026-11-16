package com.codequest.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * A floor in a castle. Floors are stacked vertically by floorNumber (1, 2, 3...).
 */
@Entity
@Table(name = "floors", uniqueConstraints = {
    @UniqueConstraint(name = "uk_level_floor_number", columnNames = {"level_id", "floor_number"})
})
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;

    @Column(name = "floor_number", nullable = false)
    private int floorNumber;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Floor() {
    }

    public Floor(Level level, int floorNumber, String title) {
        this.level = level;
        this.floorNumber = floorNumber;
        this.title = title;
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

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
