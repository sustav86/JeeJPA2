package academy.learnprogramming.service;


import academy.learnprogramming.entities.Allowance;
import academy.learnprogramming.entities.Department;
import academy.learnprogramming.entities.Employee;
import academy.learnprogramming.entities.EmployeeDetails;
import academy.learnprogramming.entities.ParkingSpace;
import academy.learnprogramming.entities.Project;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Stateless
public class QueryService {


    @Inject
    EntityManager entityManager;

    @PostConstruct
    private void init() {
    }

    @PreDestroy
    private void destroy() {

    }

    public List<Department> getAllDepartments() {
        return entityManager.createNamedQuery(Department.GET_DEPARTMENT_LIST, Department.class).getResultList();
    }


    public List<String> getAllDepartmentNames() {
        return entityManager.createNamedQuery(Department.GET_DEPARTMENT_NAMES, String.class).getResultList();
    }

    public List<ParkingSpace> getAllAllocatedParkingSpaces() {
        return entityManager.createNamedQuery(Employee.GET_ALL_PARKING_SPACES, ParkingSpace.class).getResultList();
    }

    public Collection<Object[]> getEmployeeProjection() {
        return entityManager.createNamedQuery(Employee.EMPLOYEE_PROJECTION, Object[].class).getResultList();
    }

    public List<EmployeeDetails> getEmployeeDetails() {
        return entityManager.createNamedQuery(Employee.EMPLOYEE_CONSTRUCTOR_PROJ, EmployeeDetails.class).getResultList();
    }


    public Department findDepartmentById(Long id) {
        return entityManager.find(Department.class, id);
    }

    public Employee findEmployeeById(Long id) {
        return entityManager.find(Employee.class, id);
    }

    public Collection<Allowance> getEmployeeAllowances(BigDecimal greateThanValue) {
        return entityManager.createNamedQuery(Employee.GET_EMPLOYEE_ALLOWANCES, Allowance.class).setParameter("greaterThanValue", greateThanValue).getResultList();
    }

    public Collection<Employee> getEmployeeSalaryBound(BigDecimal lowerBound, BigDecimal upperBound) {
        return entityManager.createNamedQuery(Employee.EMPLOYEE_SALARY_BOUND, Employee.class).
                setParameter("lowerBound", lowerBound).
                setParameter("upperBound", upperBound).
                getResultList();
    }

    public Collection<Employee> filterEmployees(String pattern) {
        return entityManager.createQuery("select e from Employee e where e.fullName like :filter", Employee.class)
                .setParameter("filter", pattern).getResultList();
    }

    public Employee getHighestSalary() {
        return entityManager.createQuery("select e from Employee e where e.basicSalary = (select max (emp.basicSalary) from Employee emp)", Employee.class).getSingleResult();
    }

    public Collection<Employee> filterEmoloyeeByState() {
        return entityManager.createQuery("select e from Employee e where e.address.city not in ('NY', 'CA')", Employee.class).getResultList();
    }

    public Collection<Employee> getManager() {
        return entityManager.createQuery("select e from Employee e where e.subordinates is not EMPTY", Employee.class).getResultList();
    }

    public Collection<Employee> getEmployeeByProject(Project project) {
        return entityManager.createQuery("select e from Employee e where :project member of e.projects order by e.department.departmentName desc", Employee.class)
                .setParameter("project", project).getResultList();
    }

    public Collection<Employee> getAllLowerPaidManagersAll() {
        return entityManager.createQuery("select e from Employee e where e.subordinates is not empty and e.basicSalary < all (select s.basicSalary from e.subordinates s)", Employee.class)
                .getResultList();
    }

    public Collection<Employee> getAllLowerPaidManagersAny() {
        return entityManager.createQuery("select e from Employee e where e.subordinates is not empty and e.basicSalary < any (select s.basicSalary from e.subordinates s)", Employee.class)
                .getResultList();
    }

    public Collection<Employee> getEmployeeByBonus() {
        return entityManager.createQuery("select e.basicSalary, e.basicSalary * 0.15 as bonus from Employee e order by bonus desc", Employee.class)
                .getResultList();
    }

    public List<Employee> getEmployees() {
        return null;
    }


    public List<Department> getDepartments() {
        return null;
    }
}
