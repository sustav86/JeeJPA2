package com.pedantic.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NamedQuery(name = Department.FIND_BY_ID, query = "select d from Department d where d.id = :id and d.userEmail = :email")
@NamedQuery(name = Department.FIND_BY_NAME, query = "select d from Department d where d.departmentName = :name and d.userEmail = :email")
@NamedQuery(name = Department.LIST_DEPARTMENTS, query = "select d from Department d where  d.userEmail = :email")
public class Department extends AbstractEntity {

    public static final String FIND_BY_ID = "Department.findById";
    public static final String FIND_BY_NAME = "Department.findByName";
    public static final String LIST_DEPARTMENTS = "Department.listDepartments";


    @NotEmpty(message = "Department name must be set")
    private String departmentName;

    @OneToMany(mappedBy = "department")
    private Collection<Employee> employees = new ArrayList<>();

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Collection<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Collection<Employee> employees) {
        this.employees = employees;
    }
}
