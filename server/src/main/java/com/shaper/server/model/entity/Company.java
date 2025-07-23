package  com.shaper.server.model.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Company")
@Getter
@Setter
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) 
    @Column(name = "company_id", updatable = false, nullable = false)
    private Integer companyId; 

    @Column(name = "company_name", nullable = false, unique = true)
    private String companyName;

    @Column(name = "company_email", nullable = false, unique = true)
    private String companyEmail;


    @ManyToOne
    @JoinColumn(name = "hr_User_id", nullable = false)
    private HrUser hrUser;

    
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CompanySub> subscriptions ;


}