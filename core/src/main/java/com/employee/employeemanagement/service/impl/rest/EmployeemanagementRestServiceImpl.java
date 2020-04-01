package com.employee.employeemanagement.service.impl.rest;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.domain.Page;

import com.employee.employeemanagement.logic.api.Employeemanagement;
import com.employee.employeemanagement.logic.api.to.EmployeeEto;
import com.employee.employeemanagement.logic.api.to.EmployeeSearchCriteriaTo;
import com.employee.employeemanagement.service.api.rest.EmployeemanagementRestService;

/**
 * The service implementation for REST calls in order to execute the logic of component {@link Employeemanagement}.
 */
@Named("EmployeemanagementRestService")
public class EmployeemanagementRestServiceImpl implements EmployeemanagementRestService {

  @Inject
  private Employeemanagement employeemanagement;

  @Override
  public EmployeeEto getEmployee(long id) {

    return this.employeemanagement.findEmployee(id);
  }

  @Override
  public EmployeeEto saveEmployee(EmployeeEto employee) {

    return this.employeemanagement.saveEmployee(employee);
  }

  @Override
  public void deleteEmployee(long id) {

    this.employeemanagement.deleteEmployee(id);
  }

  @Override
  public Page<EmployeeEto> findEmployees(EmployeeSearchCriteriaTo searchCriteriaTo) {

    return this.employeemanagement.findEmployees(searchCriteriaTo);
  }

}