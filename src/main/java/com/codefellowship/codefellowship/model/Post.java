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
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "applicationuser_id")
    private ApplicationUser applicationuser;


    public Post() {
    }

    public Post(String body, LocalDateTime created, ApplicationUser applicationuser) {
        this.body = body;
        this.created = created;
        this.applicationuser = applicationuser;
    }


    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public ApplicationUser getApplicationuser() {
        return applicationuser;
    }

    public void setApplicationuser(ApplicationUser applicationuser) {
        this.applicationuser = applicationuser;
    }


}
