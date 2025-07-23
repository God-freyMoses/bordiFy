package com.shaper.server.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HR_Manager")
public class HrUser extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "hr_manager_id", updatable = false, nullable = false)
    private Integer hrManagerId;

    @Column(name = "Hr_username", nullable = false, unique = true)
    private String username;

    @Column(name = "hr_password", nullable = false)
    private String password;

    @Column(name = "hr_email", nullable = false, unique = true)
    private String email;

    @Column(name = "hr_name")
    private String name;

    @Column(name = "hr_last_name")
    private String lastName;

    @Column(name = "department")
    private String department;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "createdByHr", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CompanyDepartment> createdDepartments;

    @OneToMany(mappedBy = "assignedByHr", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Template> assignedTemplates;

    

    @OneToMany(mappedBy = "registeredByHr", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Hire> registeredCandidates;
}
