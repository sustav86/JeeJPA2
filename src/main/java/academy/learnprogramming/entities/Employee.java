/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academy.learnprogramming.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;

/**
 * @author Seeraj
 */
@Entity

@NamedQuery(name = Employee.GET_ALL_PARKING_SPACES, query = "select e.parkingSpace from Employee e")
@NamedQuery(name = Employee.EMPLOYEE_PROJECTION, query = "select e.fullName, e.basicSalary from Employee e")
@NamedQuery(name = Employee.EMPLOYEE_CONSTRUCTOR_PROJ, query = "select new academy.learnprogramming.entities.EmployeeDetails(e.fullName, e.basicSalary, e.department.departmentName) from Employee  e")
@NamedQuery(name = Employee.FIND_BY_ID, query = "select e from Employee e where e.id = :id and e.userEmail = :email")
@NamedQuery(name = Employee.FIND_BY_NAME, query = "select e from Employee e where e.fullName = :name and e.userEmail = :email")
@NamedQuery(name = Employee.LIST_EMPLOYEES, query = "select  e from Employee e where e.userEmail = :email order by e.fullName")
@NamedQuery(name = Employee.FIND_PAST_PAYSLIP_BY_ID,
        query = "select p from Employee e join e.pastPayslips p where e.id = :employeeId and e.userEmail =:email and p.id =:payslipId and p.userEmail = :email")
@NamedQuery(name = Employee.GET_PAST_PAYSLIPS, query = "select p from Employee e inner join e.pastPayslips p where e.id = :employeeId and e.userEmail=:email")
//@Table(name = "Employee", schema = "HR")
public class Employee extends AbstractEntity{


//    @TableGenerator(name = "Emp_Gen", table = "ID_GEN",
//            pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE")
//    @GeneratedValue(generator = "Emp_Gen")
//    @Id
//    private Long id;


    public static final String EMPLOYEE_PROJECTION = "Employee.nameAndSalaryProjection";
    public static final String EMPLOYEE_CONSTRUCTOR_PROJ = "Employee.projection";

    public static final String FIND_BY_ID = "Employee.findById";
    public static final String FIND_BY_NAME = "Employee.findByName";
    public static final String LIST_EMPLOYEES = "Employee.listEmployees";
    public static final String FIND_PAST_PAYSLIP_BY_ID = "Employee.findPastPayslipById";
    public static final String GET_PAST_PAYSLIPS = "Employee.getPastPayslips";
    public static final String GET_ALL_PARKING_SPACES = "Employee.getAllParkingSpaces";

    @NotEmpty(message = "Name cannot be empty")
    @Basic
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

    @ManyToOne
    private Employee reportsTo;

    @OneToMany
    private Set<Employee> subordinates = new HashSet<>();


    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @Embedded
    private Address address;

    @ElementCollection
    @CollectionTable(name = "QUALIFICATIONS", joinColumns = @JoinColumn(name = "EMP_ID") )
    private Collection<Qualifications> qualifications = new ArrayList<>();

    @ElementCollection
    @Column(name = "NICKY")
    private Collection<String> nickNames = new ArrayList<>();

    private int age;

    @OneToMany( cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Allowance> employeeAllowances = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "CURRENT_PAYSLIP_ID")
    private Payslip currentPayslip;

    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private ParkingSpace parkingSpace;


    @OneToMany
    private Collection<Payslip> pastPayslips = new ArrayList<>();


    @ElementCollection
    @CollectionTable(name = "EMP_PHONE_NUMBERS")
    @MapKeyColumn(name = "PHONE_TYPE")
    @Column(name = "PHONE_NUMBER")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<PhoneType, String> employeePhoneNumbers = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "DEPT_ID")
    private Department department;

    @ManyToMany(mappedBy = "employees")
    private Collection<Project> projects = new ArrayList<>();


    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] picture;


    @PrePersist
    private void init() {
        this.age = Period.between(dateOfBirth, LocalDate.now()).getYears();
    }


    public Map<PhoneType, String> getEmployeePhoneNumbers() {
        return employeePhoneNumbers;
    }

    public void setEmployeePhoneNumbers(Map<PhoneType, String> employeePhoneNumbers) {
        this.employeePhoneNumbers = employeePhoneNumbers;
    }

    public Collection<Qualifications> getQualifications() {
        return qualifications;
    }

    public void setQualifications(Collection<Qualifications> qualifications) {
        this.qualifications = qualifications;
    }

    public Collection<String> getNickNames() {
        return nickNames;
    }

    public void setNickNames(Collection<String> nickNames) {
        this.nickNames = nickNames;
    }

    public Collection<Project> getProjects() {
        return projects;
    }

    public void setProjects(Collection<Project> projects) {
        this.projects = projects;
    }

    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public Employee getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(Employee reportsTo) {
        this.reportsTo = reportsTo;
    }

    public Set<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(Set<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

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

    public Set<Allowance> getEmployeeAllowances() {
        return employeeAllowances;
    }

    public void setEmployeeAllowances(Set<Allowance> employeeAllowances) {
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
