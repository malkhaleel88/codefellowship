package com.codefellowship.codefellowship.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


   private String body;

    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="user_id")
    private ApplicationUser user;

    public Post() {
    }

    public Post(String body) {
        this.body = body;
    }

    public Post(String body, LocalDateTime createdAt) {
        this.body = body;
        this.createdAt = createdAt;
    }

    public Post(String body, LocalDateTime createdAt, ApplicationUser user) {
        this.body = body;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createAt) {
        this.createdAt = createAt;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser applicationUser) {
        this.user = applicationUser;
    }
}
