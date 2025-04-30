package com.example.HATEOASTest.userbank;

import jakarta.persistence.*;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;

//    @Column(columnDefinition = "VARCHAR(20)", length = 20):
//    Ensures the database column is VARCHAR(20) in PostgreSQL, avoiding the ENUM type error that Hibernate 6.x might trigger.
//    @Enumerated(EnumType.STRING): Ensures Hibernate maps the Status enum to its string name (e.g., IN_PROGRESS)
//    rather than its ordinal (e.g., 0), matching your application logic and test expectations.
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)", length = 20)
    private HealthStatus status;

    // Default constructor for JPA
    protected Client() {}
    public Client(String username, String email, HealthStatus status) {
        this.email=email;
        this.username=username;
        this.status=status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HealthStatus getStatus() {
        return status;
    }

    public void setStatus(HealthStatus status) {
        this.status = status;
    }
}