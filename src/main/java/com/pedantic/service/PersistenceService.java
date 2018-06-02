package com.pedantic.service;

import com.pedantic.entities.Department;
import com.pedantic.entities.Employee;
import com.pedantic.entities.ParkingSpace;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;


@DataSourceDefinition(
        name = "java:app/Payroll/MyDS",
        className = "org.apache.derby.jdbc.ClientDriver",
        url = "jdbc:derby://localhost:1527/payroll",
        user = "appuser",
        password = "password")
@Stateless
public class PersistenceService {

    @Inject
    EntityManager entityManager;

    @Inject
    QueryService queryService;

    public void saveDepartment(Department department) {
        entityManager.persist(department);
    }

    public void removeParkingSpace(Long employeeId) {
        Employee employee = queryService.findEmployeeById(employeeId);
        ParkingSpace parkingSpace = employee.getParkingSpace();

        employee.setParkingSpace(null);

        entityManager.remove(parkingSpace);

    }

    public void saveEmployee(Employee employee, ParkingSpace parkingSpace) {

        employee.setParkingSpace(parkingSpace);
        entityManager.persist(employee);

    }

    public void updateDepartment(Department department) {
        entityManager.merge(department);
    }
}
