package  com.shaper.server.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;



@Entity
@Table(name = "Company_Subscription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanySub {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id; 

    @Enumerated(EnumType.STRING)
@Column(name = "subscription_plan", nullable = false, unique = true)
private SubscriptionPlan subscriptionPlan;


    @Column(name = "is_active", nullable = false)
    private boolean isActive; 

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date") 
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @PrePersist
    protected void onCreate() {
        startDate = LocalDateTime.now();
        isActive = true;    
}
    @PreUpdate
    protected void onUpdate() {
        if (endDate == null) {
            endDate = LocalDateTime.now();
        }
    }

    public enum SubscriptionPlan {
        BASIC,
        PREMIUM,
        ENTERPRISE
    }
}