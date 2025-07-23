package com.shaper.server.model.entity;

import com.shaper.server.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
