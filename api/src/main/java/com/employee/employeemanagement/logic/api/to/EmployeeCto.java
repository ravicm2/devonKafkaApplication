package com.employee.employeemanagement.logic.api.to;

import com.devonfw.module.basic.common.api.to.AbstractCto;

/**
 * Composite transport object of Employee
 */
public class EmployeeCto extends AbstractCto {

  private static final long serialVersionUID = 1L;

  private EmployeeEto employee;

  public EmployeeEto getEmployee() {

    return employee;
  }

  public void setEmployee(EmployeeEto employee) {

    this.employee = employee;
  }

}
