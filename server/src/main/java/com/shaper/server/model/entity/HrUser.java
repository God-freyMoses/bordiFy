
package com.shaper.server.model.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.shaper.server.model.enums.UserRole;

@Getter
@Setter
@Entity
@Table(name = "HR_Manager")
@DiscriminatorValue("HR")

@NoArgsConstructor
public class HrUser extends User {

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "createdByHr", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CompanyDepartment> departments;

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setDepartments(Set<CompanyDepartment> departments) {
        this.departments = departments;
    }

    public void setHires(Set<Hire> hires) {
        this.hires = hires;
    }

    public void setTemplates(Set<Template> templates) {
        this.templates = templates;
    }

    @OneToMany(mappedBy = "registeredByHr", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Hire> hires;
    
    @OneToMany(mappedBy = "createdByHr", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Template> templates;
    

}
