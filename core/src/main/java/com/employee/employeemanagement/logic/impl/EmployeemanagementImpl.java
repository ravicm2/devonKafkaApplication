package com.employee.employeemanagement.logic.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;

import com.employee.employeemanagement.logic.api.Employeemanagement;
import com.employee.employeemanagement.logic.api.to.EmployeeEto;
import com.employee.employeemanagement.logic.api.to.EmployeeSearchCriteriaTo;
import com.employee.employeemanagement.logic.api.usecase.UcFindEmployee;
import com.employee.employeemanagement.logic.api.usecase.UcManageEmployee;
import com.employee.general.logic.base.AbstractComponentFacade;

/**
 * Implementation of component interface of employeemanagement
 */
@Named
@Transactional
public class EmployeemanagementImpl extends AbstractComponentFacade implements Employeemanagement {

  @Inject
  private UcFindEmployee ucFindEmployee;

  @Inject
  private UcManageEmployee ucManageEmployee;

  @Override
  public EmployeeEto findEmployee(long id) {

    return this.ucFindEmployee.findEmployee(id);
  }

  @Override
  public Page<EmployeeEto> findEmployees(EmployeeSearchCriteriaTo criteria) {

    return this.ucFindEmployee.findEmployees(criteria);
  }

  @Override
  public EmployeeEto saveEmployee(EmployeeEto employee) {

    return this.ucManageEmployee.saveEmployee(employee);
  }

  @Override
  public boolean deleteEmployee(long id) {

    return this.ucManageEmployee.deleteEmployee(id);
  }

  @Override
  // TODO SSp: Please remove (see shouldSendEmployeeToKafkaAndSaveInDbWithListener)
  public void sendEmployeeToKafka(EmployeeEto message) {

    this.ucManageEmployee.sendEmployeeToKafka(message);
  }

}
