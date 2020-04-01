package com.employee.employeemanagement.logic.base.usecase;

import javax.inject.Inject;

import com.employee.employeemanagement.dataaccess.api.repo.EmployeeRepository;
import com.employee.general.logic.base.AbstractUc;

/**
 * Abstract use case for Employees, which provides access to the commonly necessary data access objects.
 */
public class AbstractEmployeeUc extends AbstractUc {

  /**
   * The constructor.
   */
  public AbstractEmployeeUc() {

    super();
  }

  /** @see #getEmployeeRepository() */
  @Inject
  private EmployeeRepository employeeRepository;

  /**
   * Returns the field 'employeeRepository'.
   *
   * @return the {@link EmployeeRepository} instance.
   */
  public EmployeeRepository getEmployeeRepository() {

    return this.employeeRepository;
  }

}
