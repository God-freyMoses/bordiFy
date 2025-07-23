package  com.shaper.server.model.entity;

import java.util.Set;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
@Table(name = "Department")
@Getter
@Setter
@NoArgsConstructor

public class CompanyDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) 
    @Column(name = "department_id", updatable = false, nullable = false)
    private Integer departmentId; 

    @Column(name = "department_name", nullable = false, unique = true)
    private String departmentName;

	@ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "hr_manager_id", nullable = false)
    private HrUser createdByHr;

    @OneToMany(mappedBy = "companyDepartment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Template> templates;

    @OneToMany(mappedBy = "assignedToCompanyDepartment", cascade = CascadeType.ALL)
    private Set<Hire> associatedCandidates;

    

}


