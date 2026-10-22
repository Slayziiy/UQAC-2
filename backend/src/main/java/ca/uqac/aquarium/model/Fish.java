package ca.uqac.aquarium.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Fish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String drawingData;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @PrePersist
    void onCreate(){ this.createdAt = Instant.now(); }

    @PreUpdate
    void onUpdate(){ this.updatedAt = Instant.now(); }

    public Fish() {
    }

    public Fish(String drawingData, User user) {
        this.drawingData = drawingData;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getDrawingData() {
        return drawingData;
    }

    public void setDrawingData(String drawingData) {
        this.drawingData = drawingData;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
