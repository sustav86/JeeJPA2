/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pedantic.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;

/**
 *
 * @author Seeraj
 */
@Entity
@NamedQuery(name=Employee.FIND_BY_ID, query = "select e from Employee e where e.id = :id and e.userEmail = :email")
@NamedQuery(name = Employee.FIND_BY_NAME, query = "select e from Employee e where e.fullName = :name and e.userEmail = :email")
@NamedQuery(name = Employee.LIST_EMPLOYEES, query = "select  e from Employee e where e.userEmail = :email")
public class Employee extends AbstractEntity{


    public static final String FIND_BY_ID = "findById";
    public static final String FIND_BY_NAME = "findByName";
    public static final String LIST_EMPLOYEES = "listEmployees";



    @NotEmpty(message = "Name cannot be empty")
    private String fullName;

    @Past(message = "Date of birth must be in the past")
    @JsonbDateFormat(value = "yyyy-MM-dd")
    private LocalDate dateOfBirth; //yyyy-MM-dd

    @NotNull(message = "Basic salary must be set")
    private BigDecimal basicSalary;

    @NotNull(message = "Hired date must be set")
    @JsonbDateFormat(value = "yyyy-MM-dd")
    @PastOrPresent(message = "Hired date must be in the past or present")
    private LocalDate hiredDate;

    @OneToMany
    private Collection<Allowance> employeeAllowances = new ArrayList<>();

    @OneToOne
    private Payslip currentPayslip;

    @OneToMany
    private Collection<Payslip> pastPayslips = new ArrayList<>();


    @ManyToOne
    private Department department;


    public Payslip getCurrentPayslip() {
        return currentPayslip;
    }

    public void setCurrentPayslip(Payslip currentPayslip) {
        this.currentPayslip = currentPayslip;
    }

    public Collection<Payslip> getPastPayslips() {
        return pastPayslips;
    }

    public void setPastPayslips(Collection<Payslip> pastPayslips) {
        this.pastPayslips = pastPayslips;
    }

    public LocalDate getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(LocalDate hiredDate) {
        this.hiredDate = hiredDate;
    }

    public Collection<Allowance> getEmployeeAllowances() {
        return employeeAllowances;
    }

    public void setEmployeeAllowances(Collection<Allowance> employeeAllowances) {
        this.employeeAllowances = employeeAllowances;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }
}
