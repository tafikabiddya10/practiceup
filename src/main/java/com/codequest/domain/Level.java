package com.codequest.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * A level represents a "castle" created by a teacher.
 * Contains narrative context from an initial NPC, stacked floors, and the final revealed code.
 */
@Entity
@Table(name = "levels")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String npcDialogue;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String finalCode;

    @Column(nullable = false)
    private boolean published = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Level() {
    }

    public Level(String title, String description, String npcDialogue, String finalCode, User teacher) {
        this.title = title;
        this.description = description;
        this.npcDialogue = npcDialogue;
        this.finalCode = finalCode;
        this.teacher = teacher;
        this.published = false;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNpcDialogue() {
        return npcDialogue;
    }

    public void setNpcDialogue(String npcDialogue) {
        this.npcDialogue = npcDialogue;
    }

    public String getFinalCode() {
        return finalCode;
    }

    public void setFinalCode(String finalCode) {
        this.finalCode = finalCode;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
