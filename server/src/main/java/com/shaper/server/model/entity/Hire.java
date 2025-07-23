package com.shaper.server.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Entity 
@Table(name = "Hire_User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hire extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "candidate_id", updatable = false, nullable = false)
    private Integer candidateId;

    @Column(name = "hire_username", nullable = false, unique = true)
    private String username; 

    @Column(name = "hire_password", nullable = false)
    private String passwordHash; 

    @Column(name = "hire_email", nullable = false, unique = true)
    private String email; 

    @Column(name = "hire_name", nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "hr_id", nullable = false)
    private HrUser registeredByHr;
    
    @ManyToOne
    @JoinColumn(name = "department_id") 
    private CompanyDepartment assignedToDepartment;
}
