package com.pedantic.service;


import com.pedantic.entities.Department;
import com.pedantic.entities.Employee;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

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

    public List<Employee> getEmployees() {
        return null;
    }


    public List<Department> getDepartments() {
        return null;
    }
}
